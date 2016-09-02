package pl.writeonly.swt.layout

import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Layout
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text
import org.eclipse.swt.SWT

class DisplayBorderLayout(val layout: Layout) {
  val display = new Display()
  val shell = new Shell(display)

  def run() = {

    shell.setLayout(layout)

    val buttonWest = new Button(shell, SWT.PUSH)
    buttonWest.setText("West")
    buttonWest.setLayoutData(BorderData.WEST)

    val buttonEast = new Button(shell, SWT.PUSH)
    buttonEast.setText("East")
    buttonEast.setLayoutData(BorderData.EAST)

    val buttonNorth = new Button(shell, SWT.PUSH)
    buttonNorth.setText("North")
    buttonNorth.setLayoutData(BorderData.NORTH)

    val buttonSouth = new Button(shell, SWT.PUSH)
    buttonSouth.setText("South")
    buttonSouth.setLayoutData(BorderData.SOUTH)

    val text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL)
    text.setText("Center")
    text.setLayoutData(BorderData.CENTER)

    shell.pack()
    shell.open()
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep()
      }
    }
    display.dispose()
  }
}


object DisplayBorderLayoutScalars extends App {
  new DisplayBorderLayout(new BorderLayoutScalars).run
}

object DisplayBorderLayoutArray extends App {
  new DisplayBorderLayout(new BorderLayoutArray).run
}