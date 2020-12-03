ROOT_DIR = .
include params.mk

.PHONY: help
help:
	@echo ""
	@echo "-- Available make targets:"
	@echo ""
	@echo "   up                         - Set up databases"
	@echo "   down                       - Clean up databases"
	@echo "   build                      - Build the library"
	@echo "   check                      - Run linters"
	@echo "   test                       - Run the tests"
	@echo "   coverage                   - Get test coverage"
	@echo "   doc                        - Build the docs"
	@echo "   site                       - Build and watch the website"
	@echo ""


.PHONY: up
up:
	@docker-compose up -d

.PHONY: down
down:
	@docker-compose down --remove-orphans

.PHONY: all
all: build check build-coverage tools doc

.PHONY: build
build:
	@bazel build //src/main/java/io/machinecode/hexane \
		$(args)

.PHONY: format
format:
	bazel build @com_github_bazelbuild_buildtools//buildifier \
				@google_java_format//jar
	find . -type f \( -name BUILD -or -name BUILD.bazel \) \
		| xargs $(BAZEL_BIN)/external/com_github_bazelbuild_buildtools/buildifier/*/buildifier
	java -jar $(BAZEL_EXEC_ROOT)/external/google_java_format/jar/downloaded.jar -i \
		$$(find src/ -type f -name '*.java')

.PHONY: check
check:
	@bazel test //...:all \
		--build_tag_filters=check \
		--test_tag_filters=check \
		$(args)

.PHONY: test
test:
	@bazel test //...:all \
		--test_tag_filters=-check \
		$(args)

.PHONY: build-coverage
build-coverage:
	@if [ -e bazel-out ]; then find bazel-out -name coverage.dat -exec rm {} +; fi
	@bazel coverage \
		//src/main/java/io/machinecode/hexane/...:all \
		//src/test/java/io/machinecode/hexane/...:all \
		--test_tag_filters=-check \
		$(args)
	@bazel build //:coverage \
		$(args)

.PHONY: coverage
coverage: build-coverage
	@mkdir -p .srv/cov
	@rm -rf .srv/cov && mkdir -p .srv/cov
	@bash -c "(cd .srv/cov && tar xf $(BAZEL_BIN)/coverage.tar)"
	@$(open) .srv/cov/index.html

.PHONY: tools
tools:
	@bazel build \
		@io_machinecode_devserver//:devserver \
		@io_machinecode_tools//tools:watch

.PHONY: doc
doc:
	@bazel build //:site \
		$(args)

.PHONY: run-site
run-site:
	$(DEV_SRV) \
		--debug=$(SITE_DEVSRV_DEBUG_PORT) \
		--dir .srv/site \
		--host $(host) \
		--port $(SITE_PORT) \
		--push-resources /,/index.html=/css/$(shell bash -c "cd .srv/site/css && find *.css"),/logo.svg,/favicon.ico \
		$(keystore) \
		$(args)

.PHONY: serve-site
serve-site: doc
	@mkdir -p .srv/site
	@rm -rf .srv/site && mkdir -p .srv/site
	@bash -c "(cd .srv/site && tar xf $(BAZEL_BIN)/site.tar)"
	@curl -fs $(transport)://$(host):$(SITE_PORT)/notify || $(open) $(transport)://$(host):$(SITE_PORT)/

.PHONY: site
site: tools
	@$(WATCH) \
		-d src/main/site \
		-c 'make run-site' \
		-w 'make serve-site'