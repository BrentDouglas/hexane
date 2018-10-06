def _coverage_html_impl(ctx):
    tar = ctx.outputs.tar
    srcs = ctx.files.srcs
    data = ctx.files.data

    mvs = []
    rels = []
    for file in data:
        rel = file.path[file.path.find("java/") + 5:]
        rels.append(rel)
        mvs.append("mkdir -p $(dirname $p/java/%s) && mv $p/%s $p/java/%s" % (rel, file.path, rel))
    for file in srcs:
        rel = file.path[file.path.find("java/") + 5:]
        mvs.append("mkdir -p $(dirname $p/java/%s) && mv $p/%s $p/java/%s" % (rel, file.path, rel))

    command = " \\\n  && ".join([
        "export PATH",
        "p=$PWD",
        "mkdir $p/java/",
        "mkdir $p/tar/",
        ] + mvs + [
        "cd $p/java/",
        "genhtml -q -o $p/tar/ \\\n" + (" \\\n".join(["    $p/java/%s" % file for file in rels])),
        "cd $p/tar/",
        "tar cf - . > $p/%s" % tar.path,
    ])
    outs = [tar]
    ctx.actions.run_shell(
        inputs = srcs + data,
        outputs = outs,
        command = command,
    )
    return struct(files = depset(outs))

coverage_html = rule(
    implementation = _coverage_html_impl,
    attrs = {
        "srcs": attr.label_list(allow_files = [".java"]),
        "data": attr.label_list(allow_files = [".dat"]),
    },
    outputs = {
        "tar": "%{name}.tar",
    },
)
"""Process a file with genhtml and output the results as html.

Args:
  srcs: The adoc files to compile.
  resources: Other files (themes, images, etc) that asciidoctor will use when
    compiling them.
  dest: The resulting name of the folder (and archive) containing the docs
    if not supplied the name of the label will be used.
  imagesdir:  A directory containing images that asciidoctor should look in.
    The contents of this director must be included in the "resources".
  attributes: Settings to pass to the asciidoctor CLI.
"""
