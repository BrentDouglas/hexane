BAZEL_BIN := $(shell bazel info bazel-bin)

# Argument to pass to the build system
a := $(shell echo "$${a:-}")
ifndef args
args := $(a)
endif

open := $(shell if [ "$$(uname)" == "Darwin" ]; then echo "open"; else echo "xdg-open"; fi)

.PHONY: all
all: help

.PHONY: help
help:
	@echo ""
	@echo "-- Available make targets:"
	@echo ""
	@echo "   bootstrap                  - Set up databases"
	@echo "   shutdown                   - Clean up databases"
	@echo "   build                      - Build the library"
	@echo "   test                       - Run the tests"
	@echo "   coverage                   - Get test coverage"
	@echo "   doc                        - Build the docs"
	@echo ""


.PHONY: bootstrap
bootstrap:
	@docker-compose up -d

.PHONY: shutdown
shutdown:
	@docker-compose down --remove-orphans

.PHONY: build
build:
	@bazel build //src/main/java/io/machinecode/hexane \
		$(args)

.PHONY: test
test:
	@bazel test //...:all \
		$(args)

.PHONY: coverage
coverage:
	@if [ -e bazel-out ]; then find bazel-out -name coverage.dat -exec rm {} +; fi
	@bazel coverage //src/test/java/io/machinecode/hexane/...:all \
		$(args)
	@bazel build //:coverage \
		$(args)
	@mkdir -p .cov
	@rm -rf .cov && mkdir -p .cov
	@bash -c "(cd .cov && tar xf $(BAZEL_BIN)/coverage.tar)"
	@$(open) .cov/index.html

.PHONY: doc
doc:
	@bazel build //:site \
		$(args)

.PHONY: site
site: doc
	@mkdir -p .srv
	@rm -rf .srv && mkdir -p .srv
	@bash -c "(cd .srv && tar zxf $(BAZEL_BIN)/site.tar.gz)"
	@$(open) .srv/docs/index.html