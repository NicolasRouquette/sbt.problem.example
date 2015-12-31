
resolvers += new MavenCache("Local Test", baseDirectory.value.getParentFile.getParentFile / "local.repo")

publishTo := Some(new MavenCache("Local Test", baseDirectory.value.getParentFile.getParentFile / "local.repo"))

addSbtPlugin("org.test" % "test-plugin" % "1.0")
