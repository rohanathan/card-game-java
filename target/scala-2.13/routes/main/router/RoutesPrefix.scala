// @GENERATOR:play-routes-compiler
// @SOURCE:/home/rohan/Downloads/java/ITSD-DT2023-24-Template/conf/routes
// @DATE:Sun Mar 02 11:33:28 GMT 2025


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
