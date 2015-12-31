sbtPlugin := true

enablePlugins(AetherPlugin)

enablePlugins(GitVersioning)

enablePlugins(GitBranchPrompt)

overridePublishBothSettings

organization := "org.test"

name := "test.plugin"

version := "1.0"

git.useGitDescribe := true

versionWithGit

// https://bintray.com/banno/oss/sbt-license-plugin/view
resolvers += Resolver.url(
  "sbt-license-plugin-releases",
  url("http://dl.bintray.com/banno/oss"))(Resolver.ivyStylePatterns)

// https://github.com/Banno/sbt-license-plugin
addSbtPlugin("com.banno" % "sbt-license-plugin" % "0.1.5")

// https://github.com/sbt/sbt-license-report
addSbtPlugin("com.typesafe.sbt" % "sbt-license-report" % "1.0.0")

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

// https://github.com/sbt/sbt-git
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.5")

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += Classpaths.sbtPluginReleases

// https://github.com/arktekk/sbt-aether-deploy
addSbtPlugin("no.arktekk.sbt" % "aether-deploy" % "0.16")

libraryDependencies += "com.typesafe" % "config" % "1.3.0"

publishMavenStyle := true

// do not include all repositories in the POM
pomAllRepositories := false

lazy val additionalProperties = settingKey[Seq[xml.Node]]("Additional entries for the POM's <properties> section")

additionalProperties :=
  <git.branch>{git.gitCurrentBranch.value}</git.branch>
  <git.commit>{git.gitHeadCommit.value.getOrElse("N/A")+(if (git.gitUncommittedChanges.value) "-SNAPSHOT" else "")}</git.commit>
  <git.tags>{git.gitCurrentTags.value}</git.tags>

pomPostProcess <<= (additionalProperties) { (additions) =>
  new xml.transform.RuleTransformer(new xml.transform.RewriteRule {
    override def transform(n: xml.Node): Seq[xml.Node] =
      n match {
        case ps: xml.Elem if ps.label == "properties" =>
	  ps.copy(child=ps.child ++ additions)
        case _ =>
          n
     }
  })
}

resolvers += new MavenCache("Local Test", baseDirectory.value.getParentFile / "local.repo")

publishTo := Some(new MavenCache("Local Test", baseDirectory.value.getParentFile / "local.repo"))
