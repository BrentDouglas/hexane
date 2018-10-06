"""
The docs
"""

load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")
load("//tools:asciidoc.bzl", "doc_to_html", "doc_to_pdf")

package(default_visibility = ["//visibility:public"])

filegroup(
    name = "srcs",
    srcs = glob(["**/*.adoc"]),
)

filegroup(
    name = "resources",
    srcs = glob(["img/**/*"]),
)

doc_to_html(
    name = "html",
    srcs = [":srcs"],
    attributes = {
        "source-highlighter": "coderay",
        "endpoint-url": "https://hexane.machinecode.io",
        "toc": "left",
        "icons": "font",
        "sectanchors": "true",
        "idprefix": "",
        "idseparator": "-",
    },
    dest = "docs",
    imagesdir = "img",
    resources = [":resources"],
)

doc_to_pdf(
    name = "pdf",
    srcs = [":srcs"],
    attributes = {
        "source-highlighter": "coderay",
        "toc": "",
        "icons": "font",
        "pagenums": "",
        "idprefix": "",
        "idseparator": "-",
    },
    dest = "docs",
    imagesdir = "img",
    resources = [
        ":resources",
    ],
)

pkg_tar(
    name = "adoc",
    srcs = [
        "index.html",
    ],
    mode = "0644",
    package_dir = "/doc",
    deps = [
        ":html",
        ":pdf",
        "//:javadoc",
    ],
)
