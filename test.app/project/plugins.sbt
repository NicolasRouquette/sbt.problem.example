
resolvers += new MavenCache("Local Test", baseDirectory.value / ".." / "local.repo")

publishTo := Some(new MavenCache("Local Test", baseDirectory.value / ".." / "local.repo"))

addSbtPlugin("org.test" % "test-plugin" % "1.0")
