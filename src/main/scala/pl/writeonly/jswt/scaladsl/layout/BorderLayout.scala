package pl.writeonly.jswt.scaladsl.layout

import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Layout
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text
import org.eclipse.swt.SWT

object BorderLayout {
  val NORTH = 0
  val SOUTH = 1
  val CENTER = 2
  val EAST = 3
  val WEST = 4
}

case class BorderData(val data: Int) {
  def this() = this(BorderLayout.CENTER)
}

object BorderData {
  val NORTH = BorderData(BorderLayout.NORTH)
  val SOUTH = BorderData(BorderLayout.SOUTH)
  val CENTER = BorderData(BorderLayout.CENTER)
  val EAST = BorderData(BorderLayout.EAST)
  val WEST = BorderData(BorderLayout.WEST)
}


