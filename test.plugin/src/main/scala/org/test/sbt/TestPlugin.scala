package org.test.sbt

import java.io.File
import java.util.{Calendar, Locale}

import com.typesafe.config.{ConfigFactory, Config}

import sbt.Keys._
import sbt._

import scala.util.{Failure, Success, Try}

object TestPlugin extends TestPlugin {

  override def trigger = allRequirements

  override def requires =
    aether.AetherPlugin

  override def buildSettings: Seq[Setting[_]] =
    Seq()

  override def projectSettings: Seq[Setting[_]] = 
    Seq()

}

trait TestPlugin extends AutoPlugin {

  /**
   * Lookup the location of the runtime library for a specific version of the JDK
   *
   * @param config The Config to lookup the JDK installation location
   * @param versionProperty The Config property path of the form 'jdk_locations.1.<N>' used to lookup the JDK location
   *                        or to map to environment property of the form 'jdk_locations_1_<N>'
   * @return A pair of the JDK version (1.<N>) and, optionally,
   *         the location of the jre/lib/rt.jar in the JDK installation folder
   */
  def getJRERuntimeLib(config: Config, versionProperty: String): (String, Option[File]) = {
    val versionEnv = versionProperty.replace('.', '_')
    val versionKey = versionProperty.stripPrefix("jdk_locations.").replace('_', '.')
    Try(config.getString(versionProperty))
    .orElse(Try(config.getString(versionEnv))) match {
      case Success(location) =>
        val rtLib = Path(location) / "jre" / "lib" / "rt.jar"
        if (rtLib.exists && !rtLib.isDirectory && rtLib.asFile.canRead)
          (versionKey, Some(rtLib.asFile))
        else
          (versionKey, None)
      case Failure(_)        =>
        (versionKey, None)
    }
  }

  /**
   * Computes the additional JDK version-specific content to append to javacOptions
   *
   * @see https://blogs.oracle.com/darcy/entry/bootclasspath_older_source
   * @see https://blogs.oracle.com/darcy/entry/how_to_cross_compile_for
   *
   * @param jdk JDK version & installation location, if available
   * @return content to append to javacOptions
   */
  def getJavacOptionsForJDKIfAvailable(jdk: SettingKey[(String, Option[File])])
  : Def.Initialize[Task[Seq[String]]] =
    Def.task[Seq[String]] {
    jdk.value match {
    case (version, Some(loc)) =>
      Seq(
        "-source", version,
        "-target", version,
        "-bootclasspath", loc.absolutePath)
    case (version, None) =>
      sLog.value.warn(
        "No configuration or property information for "+
        jdk.key.description.getOrElse(jdk.key.label))
      Seq(
        "-source", version,
        "-target", version)
    }
  }

  /**
   * Computes the additional JDK version-specific content to append to scalacOptions
   *
   * @see http://stackoverflow.com/questions/32419353/
   * @see https://blogs.oracle.com/darcy/entry/bootclasspath_older_source
   * @see https://blogs.oracle.com/darcy/entry/how_to_cross_compile_for
   *
   * @param jdk JDK version & installation location, if available
   * @return content to append to scalacOptions
   */
  def getScalacOptionsForJDKIfAvailable(jdk: SettingKey[(String, Option[File])]) = Def.task[Seq[String]] {
    jdk.value match {
    case (version, Some(loc)) =>
      Seq(
        "-target:jvm-"+version,
        "-javabootclasspath", loc.absolutePath)
    case (version, None) =>
      sLog.value.warn(
        "No configuration or property information for "+
        jdk.key.description.getOrElse(jdk.key.label))
      Seq(
        "-target:jvm-"+version)
    }
  }

  /**
   * @see https://tpolecat.github.io/2014/04/11/scalac-flags.html
   * @return SBT settings
   */
  def useStrictScalacFatalWarningsSettings: Seq[Setting[_]] =
    Seq(
      scalacOptions ++= Seq(
        "-deprecation",
        "-encoding", "UTF-8",     // yes, this is 2 args
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-unchecked",
        "-Xfatal-warnings",
        "-Xlint",
        "-Yno-adapted-args",
        "-Ywarn-dead-code",       // N.B. doesn't work well with the ??? hole
        "-Ywarn-numeric-widen",
        "-Ywarn-value-discard",
        "-Xfuture",
        "-Ywarn-unused-import",   // 2.11 only
        "-Yno-imports"            // no automatic imports at all; all symbols must be imported explicitly
      ))
}