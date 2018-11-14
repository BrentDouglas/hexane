

def is_archive(path):
  return is_zip(path) or is_jar(path) or is_tar(path)

def is_zip(path):
  return path.endswith(".zip")

def is_any_jar(path):
  return is_jar(path) or is_srcjar(path)

def is_jar(path):
  return path.endswith(".jar")

def is_srcjar(path):
  return path.endswith(".srcjar")

def is_any_tar(path):
  return is_tgz(path) or is_tbz(path) or is_tar(path)

def is_tar(path):
  return path.endswith(".tar")

def is_tgz(path):
  return path.endswith(".tgz") or path.endswith(".tar.gz")

def is_tbz(path):
  return path.endswith(".tar.bz2") or path.endswith(".tar.bzip2")

def list_file(path, sep):
    return sep.join([
        "jar tf %s" % (path) if is_jar(path) else "cat %s | tar tf -" % (path),
    ]) + sep

def extract_file(path):
    return " \\\n  && ".join([
        "jar xf %s" % (path) if is_jar(path) else "cat %s | tar xf -" % (path),
    ])

def join_list(prefix, list):
    n = len(list)
    ret = ""
    for i in range(0, n):
        it = list[i]
        if not it:
          continue
        if ret:
          ret += ","
        if i != 0:
          ret += "\n"
        ret += prefix + it
    return ret


def join_dict(prefix, dict):
    n = len(dict)
    ret = ""
    i = 0
    for k in dict:
        v = dict[k]
        if ret:
          ret += ","
        if i != 0:
          ret += "\n"
        ret += prefix + '"%s": "%s"' % (k, v)
        i += 1
    return ret

def _repack_archive(unpack, pack, dir, in_path, out_path):
  """Repackage an archive

  Args:
    unpack: The command to unpack the original archive
    pack: The command to pack the new archive
    dir: An optional dir to move the content into within the tarball
    in_path: The path to the original archive
    out_path: The path to the new archive
  """
  act_dir = "test" if not dir else "test/%s" % dir
  return " \\\n  && " + " \\\n  && ".join([
      "mkdir -p %s" % act_dir,
      "(cd %s && %s $p/%s >/dev/null)" % (act_dir, unpack, in_path),
      "(cd test && %s $p/%s .)" % (pack, out_path),
      "rm -rf test",
  ])

def _to_tar_impl(ctx):
    srcs = ctx.files.srcs
    ext = ctx.attr.extension
    dir = ctx.attr.dir
    tar = "tar cf"
    if is_tgz(ext):
        tar = "tar czf"
    if is_tbz(ext):
        tar = "tar cjf"
    cmd = " \\\n  && ".join([
        "export PATH",
        "p=$PWD",
    ])
    outs = []
    for file in srcs:
        if is_jar(file.path):
            out = ctx.actions.declare_file(file.basename[:4] + ext)
            outs.append(out)
            cmd += _repack_archive("jar xf", tar, dir, file.path, out.path)
        if is_srcjar(file.path):
            out = ctx.actions.declare_file(file.basename[:7] + ext)
            outs.append(out)
            cmd += _repack_archive("jar xf", tar, dir, file.path, out.path)
        if is_zip(file.path):
            out = ctx.actions.declare_file(file.basename[:4] + ext)
            outs.append(out)
            cmd += _repack_archive("unzip", tar, dir, file.path, out.path)
        if is_tar(file.path):
            out = ctx.actions.declare_file(file.basename[:4] + ext)
            outs.append(out)
            cmd += _repack_archive("tar xf", tar, dir, file.path, out.path)
        if is_tgz(file.path):
            n = 4 if file.path.endswith(".tgz") else 7
            out = ctx.actions.declare_file(file.basename[:n] + ext)
            outs.append(out)
            cmd += _repack_archive("tar zxf", tar, dir, file.path, out.path)
        if is_tbz(file.path):
            n = 7 if file.path.endswith(".tar.bz2") else 9
            out = ctx.actions.declare_file(file.basename[:n] + ext)
            outs.append(out)
            cmd += _repack_archive("tar jxf", tar, dir, file.path, out.path)
    ctx.actions.run_shell(
        inputs = srcs,
        outputs = outs,
        command = cmd,
    )
    return struct(
        files = depset(outs),
    )

to_tar = rule(
    implementation = _to_tar_impl,
    attrs = {
        "srcs": attr.label_list(allow_files = [
            ".zip",
            ".jar",
            ".srcjar",
            ".tar",
            ".tgz",
            ".tar.gz",
            ".tar.bz2",
            ".tar.bzip2",
        ]),
        "dir": attr.string(),
        "extension": attr.string(mandatory = False, default = ".tar"),
    },
)
"""Repack an archive as the provided type of tarball

Args:
  srcs: The files to be converted to tarballs
  dir: An optional dir to move the content into within the tarball
  extension: The output extension of the tarballs. It must start with a dot. Defaults to '.tar'
"""
