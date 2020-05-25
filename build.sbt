ThisBuild / scalaVersion := "2.13.1"
ThisBuild / organization := "com.tautvidas"

lazy val cryptopals = (project in file("."))
  .settings(
    name := "cryptopals",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  )
