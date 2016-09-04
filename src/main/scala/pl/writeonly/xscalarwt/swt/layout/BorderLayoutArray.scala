package pl.writeonly.xscalarwt.swt.layout

import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Layout
import org.eclipse.swt.SWT

import scala.Array.canBuildFrom

class BorderLayoutArray extends Layout {

  val controls = new Array[Control](5)
  var sizes: Array[Point] = _
  var width = 0
  var height = 0

  protected def computeSize(composite: Composite, wHint: Int, hHint: Int, flushCache: Boolean): Point = {
    if (sizes == null || flushCache) refreshSizes(composite.getChildren())
    return new Point(if (wHint != SWT.DEFAULT) wHint else width, if (hHint != SWT.DEFAULT) hHint else hHint)
  }

  protected def layout(composite: Composite, flushCache: Boolean) = {
    if (flushCache || sizes == null) refreshSizes(composite.getChildren())
    val clientArea = composite.getClientArea()

    if (controls(BorderLayout.NORTH) != null) {
      controls(BorderLayout.NORTH).setBounds(
        clientArea.x,
        clientArea.y,
        clientArea.width,
        sizes(BorderLayout.NORTH).y)
    }
    if (controls(BorderLayout.SOUTH) != null) {
      controls(BorderLayout.SOUTH).setBounds(
        clientArea.x,
        clientArea.y + clientArea.height - sizes(BorderLayout.SOUTH).y,
        clientArea.width,
        sizes(BorderLayout.SOUTH).y)
    }
    if (controls(BorderLayout.WEST) != null) {
      controls(BorderLayout.WEST).setBounds(
        clientArea.x,
        clientArea.y + sizes(BorderLayout.NORTH).y,
        sizes(BorderLayout.WEST).x,
        clientArea.height - sizes(BorderLayout.NORTH).y - sizes(BorderLayout.SOUTH).y)
    }
    if (controls(BorderLayout.EAST) != null) {
      controls(BorderLayout.EAST).setBounds(
        clientArea.x + clientArea.width - sizes(BorderLayout.EAST).x,
        clientArea.y + sizes(BorderLayout.NORTH).y,
        sizes(BorderLayout.EAST).x,
        clientArea.height - sizes(BorderLayout.NORTH).y - sizes(BorderLayout.SOUTH).y)
    }
    if (controls(BorderLayout.CENTER) != null) {
      controls(BorderLayout.CENTER).setBounds(
        clientArea.x + sizes(BorderLayout.WEST).x,
        clientArea.y + sizes(BorderLayout.NORTH).y,
        clientArea.width - sizes(BorderLayout.WEST).x - sizes(BorderLayout.EAST).x,
        clientArea.height - sizes(BorderLayout.NORTH).y - sizes(BorderLayout.SOUTH).y)
    }
  }

  private def refreshSizes(children: Array[Control]) {
    for (child <- children) yield {
      val layoutData = child.getLayoutData()
      if (layoutData != null) {
        val data = layoutData.asInstanceOf[BorderData].data
        if (0 <= data && data <= 4) {
          controls(data) = child
        } else {
          controls(BorderLayout.CENTER) = child
        }
      }
    }

    width = 0
    height = 0

    if (sizes == null) sizes = new Array[Point](5)

    for (i <- 0 until controls.length) yield {
      sizes(i) = if (controls(i) != null) controls(i).computeSize(SWT.DEFAULT, SWT.DEFAULT, true) else new Point(0, 0)
    }

    width = math.max(width, sizes(BorderLayout.NORTH).x)
    width = math.max(width, sizes(BorderLayout.WEST).x + sizes(BorderLayout.CENTER).x + sizes(BorderLayout.EAST).x)
    width = math.max(width, sizes(BorderLayout.SOUTH).x)

    height = math.max(math.max(sizes(BorderLayout.WEST).y, sizes(BorderLayout.EAST).y), sizes(BorderLayout.CENTER).y)
    +sizes(BorderLayout.NORTH).y
    +sizes(BorderLayout.SOUTH).y
  }
}


