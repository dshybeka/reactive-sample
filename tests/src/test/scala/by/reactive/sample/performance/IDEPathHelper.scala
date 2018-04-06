package by.reactive.sample.performance

import scala.tools.nsc.io.File
import scala.tools.nsc.io.Path.string2path

object IDEPathHelper {

	val gatlingConfUrl = getClass.getClassLoader.getResource("application.conf").getPath
	val projectRootDir = File(gatlingConfUrl).parents(2)

	val mavenSourcesDirectory = projectRootDir / "src" / "main" / "scala"
	val mavenResourcesDirectory = projectRootDir / "src" / "main" / "resources"
	val mavenTargetDirectory = projectRootDir / "target"
	val mavenBinariesDirectory = mavenTargetDirectory / "test-classes"

	val dataDirectory = mavenResourcesDirectory / "data"

	val recorderOutputDirectory = mavenSourcesDirectory
	val resultsDirectory = mavenTargetDirectory / "results"
}
