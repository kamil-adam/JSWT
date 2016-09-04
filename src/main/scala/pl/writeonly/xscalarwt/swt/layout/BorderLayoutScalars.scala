package pl.writeonly.xscalarwt.swt.layout

import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Layout
import org.eclipse.swt.SWT

class BorderLayoutScalars extends Layout {
  var north: Control = _
  var south: Control = _
  var east: Control = _
  var west: Control = _
  var center: Control = _

  protected def computeSize(composite: Composite, wHint: Int, hHint: Int, flushCache: Boolean): Point = {
    getControls(composite)

    val w1 = (if (west != null) getSize(west, flushCache).x else 0) + (if (east != null) getSize(east, flushCache).x else 0) + (if (center != null) getSize(center, flushCache).x else 0)
    val w2 = if (north != null) { math.max(w1, getSize(north, flushCache).x) } else w1
    val w3 = if (south != null) { math.max(w2, getSize(south, flushCache).x) } else w2

    val h0 = (if (north != null) getSize(north, flushCache).y else 0) + (if (south != null) getSize(south, flushCache).y else 0)
    val h1 = if (center != null) getSize(center, flushCache).y else 0
    val h2 = if (west != null) { math.max(h1, getSize(west, flushCache).y) } else h1
    val h3 = if (east != null) { math.max(h2, getSize(east, flushCache).y) } else h2

    val h4 = h0 + h3

    return new Point(math.max(w3, wHint), math.max(h4, hHint))
  }

  protected def layout(composite: Composite, flushCache: Boolean) = {
    getControls(composite)
    val rect = composite.getClientArea()
    var left = rect.x
    var right = rect.width
    var top = rect.y
    var bottom = rect.height
    if (north != null) {
      val pt = getSize(north, flushCache)
      north.setBounds(left, top, rect.width, pt.y)
      top += pt.y
    }
    if (south != null) {
      val pt = getSize(south, flushCache)
      south.setBounds(left, rect.height - pt.y, rect.width, pt.y)
      bottom -= pt.y
    }
    if (east != null) {
      val pt = getSize(east, flushCache)
      east.setBounds(rect.width - pt.x, top, pt.x, (bottom - top))
      right -= pt.x
    }
    if (west != null) {
      val pt = getSize(west, flushCache)
      west.setBounds(left, top, pt.x, (bottom - top))
      left += pt.x
    }
    if (center != null) {
      center.setBounds(left, top, (right - left), (bottom - top))
    }
  }

  protected def getSize(control: Control, flushCache: Boolean): Point = {
    return control.computeSize(SWT.DEFAULT, SWT.DEFAULT, flushCache)
  }

  private def region(region: BorderData, child: Control): Any = region match {
    case BorderData.NORTH => north = child
    case BorderData.SOUTH => south = child
    case BorderData.EAST => east = child
    case BorderData.WEST => west = child
    case _ => center = child
  }

  protected def getControls(composite: Composite) = {
    for (child <- composite.getChildren()) {
      val data = child.getLayoutData()
      data match {
        case data: BorderData => region(data, child)
        case _ => center = child
      }
    }
  }
}


