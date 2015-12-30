# publish an SBT plugin to a local maven repo

```
cd test.plugin
sbt publish
```

# check the local repo

```
./local.repo/org/test/test-plugin_2.10_0.13/1.0
test-plugin-1.0.jar
test-plugin-1.0.jar.md5
test-plugin-1.0.jar.sha1
test-plugin-1.0-javadoc.jar
test-plugin-1.0-javadoc.jar.md5
test-plugin-1.0-javadoc.jar.sha1
test-plugin-1.0.pom
test-plugin-1.0.pom.md5
test-plugin-1.0.pom.sha1
test-plugin-1.0-sources.jar
test-plugin-1.0-sources.jar.md5
test-plugin-1.0-sources.jar.sha1
```

In particular, the POM file:

```
<?xml version='1.0' encoding='UTF-8'?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.test</groupId>
    <artifactId>test-plugin</artifactId>
    <packaging>jar</packaging>
    <description>test.plugin</description>
    <version>1.0</version>
    <name>test.plugin</name>
    <organization>
        <name>org.test</name>
    </organization>
    <properties>
        <git.branch>master</git.branch>
        <git.commit>17d8eed0c2a31b6a27b8e35966428bd25c290830</git.commit>
        <git.tags></git.tags>
    </properties>
    <properties>
        <sbtVersion>0.13</sbtVersion>
        <scalaVersion>2.10</scalaVersion>
        <extraDependencyAttributes xml:space="preserve">+e:scalaVersion:#@#:+2.10:#@#:+revision:#@#:+0.1.5:#@#:+module:#@#:+sbt-license-plugin:#@#:+e:sbtVersion:#@#:+0.13:#@#:+organisation:#@#:+com.banno:#@#:+branch:#@#:+@#:NULL:#@:#@#:
+e:scalaVersion:#@#:+2.10:#@#:+revision:#@#:+1.0.0:#@#:+module:#@#:+sbt-license-report:#@#:+e:sbtVersion:#@#:+0.13:#@#:+organisation:#@#:+com.typesafe.sbt:#@#:+branch:#@#:+@#:NULL:#@:#@#:
+e:scalaVersion:#@#:+2.10:#@#:+revision:#@#:+0.8.5:#@#:+module:#@#:+sbt-git:#@#:+e:sbtVersion:#@#:+0.13:#@#:+organisation:#@#:+com.typesafe.sbt:#@#:+branch:#@#:+@#:NULL:#@:#@#:
+e:scalaVersion:#@#:+2.10:#@#:+revision:#@#:+0.16:#@#:+module:#@#:+aether-deploy:#@#:+e:sbtVersion:#@#:+0.13:#@#:+organisation:#@#:+no.arktekk.sbt:#@#:+branch:#@#:+@#:NULL:#@:#@#:</extraDependencyAttributes>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-library</artifactId>
            <version>2.10.5</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.scala-sbt</groupId>
            <artifactId>sbt</artifactId>
            <version>0.13.9</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.banno</groupId>
            <artifactId>sbt-license-plugin</artifactId>
            <version>0.1.5</version>
        </dependency>
        <dependency>
            <groupId>com.typesafe.sbt</groupId>
            <artifactId>sbt-license-report</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>com.typesafe.sbt</groupId>
            <artifactId>sbt-git</artifactId>
            <version>0.8.5</version>
        </dependency>
        <dependency>
            <groupId>no.arktekk.sbt</groupId>
            <artifactId>aether-deploy</artifactId>
            <version>0.16</version>
        </dependency>
        <dependency>
            <groupId>com.typesafe</groupId>
            <artifactId>config</artifactId>
            <version>1.3.0</version>
        </dependency>
    </dependencies>
    <repositories>
        <repository>
            <id>jgitrepo</id>
            <name>jgit-repo</name>
            <url>http://download.eclipse.org/jgit/maven</url>
            <layout>default</layout>
        </repository>
        <repository>
            <id>sonatypereleases</id>
            <name>sonatype-releases</name>
            <url>https://oss.sonatype.org/content/repositories/releases/</url>
            <layout>default</layout>
        </repository>
    </repositories>
</project>
```

# Run SBT in the test.app which uses the test.plugin

```
cd test.app
sbt
```

This fails:

```
[774] $ sbt
[info] Loading global plugins from /home/rouquett/.sbt/0.13/plugins
[info] Loading project definition from /opt/local/imce/users/nfr/github.imce/example/test.app/project
[info] Updating {file:/opt/local/imce/users/nfr/github.imce/example/test.app/project/}test-app-build...
[info] Resolving org.test#test-plugin;1.0 ...
[warn]  module not found: org.test#test-plugin;1.0
[warn] ==== typesafe-ivy-releases: tried
[warn]   https://repo.typesafe.com/typesafe/ivy-releases/org.test/test-plugin/scala_2.10/sbt_0.13/1.0/ivys/ivy.xml
[warn] ==== sbt-plugin-releases: tried
[warn]   https://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/org.test/test-plugin/scala_2.10/sbt_0.13/1.0/ivys/ivy.xml
[warn] ==== local: tried
[warn]   /home/rouquett/.ivy2/local/org.test/test-plugin/scala_2.10/sbt_0.13/1.0/ivys/ivy.xml
[warn] ==== jcenter: tried
[warn]   https://jcenter.bintray.com/org/test/test-plugin_2.10_0.13/1.0/test-plugin-1.0.pom
[warn] ==== public: tried
[warn]   https://repo1.maven.org/maven2/org/test/test-plugin_2.10_0.13/1.0/test-plugin-1.0.pom
[warn] ==== Local Test: tried
[warn]   file:/opt/local/imce/users/nfr/github.imce/example/test.app/project/../local.repo/org/test/test-plugin_2.10_0.13/1.0/test-plugin-1.0.pom
[warn] ==== jetbrains-bintray: tried
[warn]   http://dl.bintray.com/jetbrains/sbt-plugins/org.test/test-plugin/scala_2.10/sbt_0.13/1.0/ivys/ivy.xml
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[warn]  ::::::::::::::::::::::::::::::::::::::::::::::
[warn]  ::          UNRESOLVED DEPENDENCIES         ::
[warn]  ::::::::::::::::::::::::::::::::::::::::::::::
[warn]  :: org.test#test-plugin;1.0: not found
[warn]  ::::::::::::::::::::::::::::::::::::::::::::::
[warn] 
[warn]  Note: Some unresolved dependencies have extra attributes.  Check that these dependencies exist with the requested attributes.
[warn]          org.test:test-plugin:1.0 (sbtVersion=0.13, scalaVersion=2.10)
[warn] 
[warn]  Note: Unresolved dependencies path:
[warn]          org.test:test-plugin:1.0 (sbtVersion=0.13, scalaVersion=2.10) (/opt/local/imce/users/nfr/github.imce/example/test.app/project/plugins.sbt#L6-7)
[warn]            +- default:test-app-build:0.1-SNAPSHOT (sbtVersion=0.13, scalaVersion=2.10)
sbt.ResolveException: unresolved dependency: org.test#test-plugin;1.0: not found
        at sbt.IvyActions$.sbt$IvyActions$$resolve(IvyActions.scala:294)
        at sbt.IvyActions$$anonfun$updateEither$1.apply(IvyActions.scala:191)
        at sbt.IvyActions$$anonfun$updateEither$1.apply(IvyActions.scala:168)
        at sbt.IvySbt$Module$$anonfun$withModule$1.apply(Ivy.scala:155)
        at sbt.IvySbt$Module$$anonfun$withModule$1.apply(Ivy.scala:155)
        at sbt.IvySbt$$anonfun$withIvy$1.apply(Ivy.scala:132)
        at sbt.IvySbt.sbt$IvySbt$$action$1(Ivy.scala:57)
        at sbt.IvySbt$$anon$4.call(Ivy.scala:65)
        at xsbt.boot.Locks$GlobalLock.withChannel$1(Locks.scala:93)
        at xsbt.boot.Locks$GlobalLock.xsbt$boot$Locks$GlobalLock$$withChannelRetries$1(Locks.scala:78)
        at xsbt.boot.Locks$GlobalLock$$anonfun$withFileLock$1.apply(Locks.scala:97)
        at xsbt.boot.Using$.withResource(Using.scala:10)
        at xsbt.boot.Using$.apply(Using.scala:9)
        at xsbt.boot.Locks$GlobalLock.ignoringDeadlockAvoided(Locks.scala:58)
        at xsbt.boot.Locks$GlobalLock.withLock(Locks.scala:48)
        at xsbt.boot.Locks$.apply0(Locks.scala:31)
        at xsbt.boot.Locks$.apply(Locks.scala:28)
        at sbt.IvySbt.withDefaultLogger(Ivy.scala:65)
        at sbt.IvySbt.withIvy(Ivy.scala:127)
        at sbt.IvySbt.withIvy(Ivy.scala:124)
        at sbt.IvySbt$Module.withModule(Ivy.scala:155)
        at sbt.IvyActions$.updateEither(IvyActions.scala:168)
        at sbt.Classpaths$$anonfun$sbt$Classpaths$$work$1$1.apply(Defaults.scala:1392)
        at sbt.Classpaths$$anonfun$sbt$Classpaths$$work$1$1.apply(Defaults.scala:1388)
        at sbt.Classpaths$$anonfun$doWork$1$1$$anonfun$90.apply(Defaults.scala:1422)
        at sbt.Classpaths$$anonfun$doWork$1$1$$anonfun$90.apply(Defaults.scala:1420)
        at sbt.Tracked$$anonfun$lastOutput$1.apply(Tracked.scala:37)
        at sbt.Classpaths$$anonfun$doWork$1$1.apply(Defaults.scala:1425)
        at sbt.Classpaths$$anonfun$doWork$1$1.apply(Defaults.scala:1419)
        at sbt.Tracked$$anonfun$inputChanged$1.apply(Tracked.scala:60)
        at sbt.Classpaths$.cachedUpdate(Defaults.scala:1442)
        at sbt.Classpaths$$anonfun$updateTask$1.apply(Defaults.scala:1371)
        at sbt.Classpaths$$anonfun$updateTask$1.apply(Defaults.scala:1325)
        at scala.Function1$$anonfun$compose$1.apply(Function1.scala:47)
        at sbt.$tilde$greater$$anonfun$$u2219$1.apply(TypeFunctions.scala:40)
        at sbt.std.Transform$$anon$4.work(System.scala:63)
        at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:226)
        at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:226)
        at sbt.ErrorHandling$.wideConvert(ErrorHandling.scala:17)
        at sbt.Execute.work(Execute.scala:235)
        at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:226)
        at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:226)
        at sbt.ConcurrentRestrictions$$anon$4$$anonfun$1.apply(ConcurrentRestrictions.scala:159)
        at sbt.CompletionService$$anon$2.call(CompletionService.scala:28)
        at java.util.concurrent.FutureTask.run(FutureTask.java:262)
        at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:471)
        at java.util.concurrent.FutureTask.run(FutureTask.java:262)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
        at java.lang.Thread.run(Thread.java:745)
[error] (*:update) sbt.ResolveException: unresolved dependency: org.test#test-plugin;1.0: not found
Project loading failed: (r)etry, (q)uit, (l)ast, or (i)gnore? 
```
