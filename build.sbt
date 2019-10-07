ThisBuild / scalaVersion := "2.12.10"
ThisBuild / organization := "com.tautvidas"

lazy val cryptopals = (project in file("."))
  .settings(
    name := "cryptopals",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  )
