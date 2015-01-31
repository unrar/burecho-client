/**
 * This file is property of INSURV (c) 2014
 * BURECHO is a program property of INSURV (c) 2014
 *
 * This is the BURECHO client, implemented in Scala. It connects to a BURECHO server (burechoServer) through
 * a given port (bsPort, default=4303) and sends some information.
 *
 * Usage: scala BurechoClient [port]
 */

import java.net._
import java.io._
import scala.io._

object BurechoClient {
  /*
   * Configuration section.
   * burechoServer -> server IP (default 127.0.0.1)
   * bsPort -> port (default 4303)
   */
  var burechoServer = "127.0.0.1"
  var bsPort = 4303
  def main(args: Array[String]) {
    if (args.length > 1) {
      this.bsPort = args(1).toInt
      this.burechoServer = args(0)
    }
    // Set up sockets
    try {
      val s = new Socket(InetAddress.getByName(burechoServer), bsPort)
      lazy val in = new BufferedSource(s.getInputStream()).getLines()
      val out = new PrintStream(s.getOutputStream())

      // Start a loop
      var keepOn = true
      while (keepOn) {
        val uInput = StdIn.readLine("> send to server: ")
        out.println(uInput.trim())
        out.flush()
        val sInput = in.next()
        println(sInput)
        if (sInput.trim() == ">> BYE") {
          keepOn = false
        }
      }
    }
    catch {
      case ce: ConnectException => println("Connection refused!")
      case e: Exception => println("Unknown exception caught!")
    }
  }
}
