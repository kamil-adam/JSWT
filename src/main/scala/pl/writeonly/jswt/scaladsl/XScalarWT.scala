package pl.writeonly.jswt.scaladsl
import org.eclipse.jface.viewers.CellEditor
import org.eclipse.jface.viewers.CheckboxCellEditor
import org.eclipse.jface.viewers.ComboBoxCellEditor
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.SWT
import org.eclipse.swt.custom._
import org.eclipse.swt.layout._
import org.eclipse.swt.widgets._
import pl.writeonly.xscalawt._
import pl.writeonly.xscalawt.XScalaWT._
import pl.writeonly.xscalawt.XScalaWTAPI._
import pl.writeonly.xscalawt.viewers.TableViewerBuilder
import pl.writeonly.xscalarwt.swt.layout._



object XScalarWT {
  implicit def textToString(text: Text) = text.getText
  implicit def labelToString(label: Label) = label.getText

  def mainLoop(window: Shell) = {
    val display = Display.getDefault
    window.open
    while (!window.isDisposed) {
      if (!display.readAndDispatch) display.sleep
    }
  }

  def checkbox(setups: (Button => Any)*) = { (parent: Composite) =>
    setupAndReturn(new Button(parent, SWT.PUSH), setups: _*)
  }

  // Layouts here
  def feelLayout(setups: (FillLayout => Any)*) = (c: Composite) => {
    c.setLayout(setupAndReturn(new FillLayout, setups: _*))
  }
  def stackLayout(setups: (StackLayout => Any)*) = (c: Composite) => {
    c.setLayout(setupAndReturn(new StackLayout, setups: _*))
  }
  def borderLayoutScalars(setups: (BorderLayoutScalars => Any)*) = (c: Composite) => {
    c.setLayout(setupAndReturn(new BorderLayoutScalars, setups: _*))
  }
  def borderLayoutArray(setups: (BorderLayoutArray => Any)*) = (c: Composite) => {
    c.setLayout(setupAndReturn(new BorderLayoutArray, setups: _*))
  }

  def tableViewerBuilderCheck[A](setups: (TableViewerBuilder[A] => Any)*)(viewerSetups: (TableViewer => Any)*) = (parent: Composite) => {
    //    val builder = setupAndReturn(new TableViewerBuilder[A](parent, SWT.CHECK| SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER), setups: _*)
    val builder = setupAndReturn(new TableViewerBuilder[A](parent, SWT.CHECK | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER, true, true, true), setups: _*)
    val viewer = builder.viewer
    viewerSetups.foreach(_(viewer))
    builder
  }

  def textArea(setups: (Text => Any)*)(parent: Composite) = {
    setupAndReturn(new Text(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL), setups: _*)
  }
  def toolItem(setups: (ToolItem => Any)*)(parent: ToolBar) = {
    setupAndReturn(new ToolItem(parent, SWT.PUSH), setups: _*)
  }

  def sashHorizontal(setups: (SashForm => Any)*) = { (parent: Composite) =>
    setupAndReturn(new SashForm(parent, SWT.HORIZONTAL), setups: _*)
  }

  def sashVerdical(setups: (SashForm => Any)*) = { (parent: Composite) =>
    setupAndReturn(new SashForm(parent, SWT.VERTICAL), setups: _*)
  }

  def fileDialogOpen(setups: (FileDialog => Any)*)(parent: Shell) = {
    setupAndReturn(new FileDialog(parent, SWT.OPEN), setups: _*)
  }
  def fileDialogSave(setups: (FileDialog => Any)*)(parent: Shell) = {
    setupAndReturn(new FileDialog(parent, SWT.SAVE), setups: _*)
  }

  def checkboxCellEditorControl(control: Composite): CellEditor = {
    //logger debug "checkboxCellEditorControl control => " + control
    new CheckboxCellEditor(control, SWT.CHECK)
  }

  def comboCellEditorControl(control: Composite): CellEditor = {
    //logger debug "checkboxCellEditorControl control => " + control
    new ComboBoxCellEditor(control, Array[String]("STRING"))
  }

  def applicationWindow(setups: (ApplicationWindow => Any)*)(shell: Shell) {
    val app = new ApplicationWindow(shell)
    setups.foreach(setup => setup(app))
  }

  def shellDefault() = {
    val shell = new Shell(Display.getDefault)
    shell.setLayout(new GridLayout());
    shell
  }

  def contentDefault(setups: (Shell => Any)*)(parent: Shell) = {
    setups.foreach(setup => setup(parent))
    parent
  }

  def content(setups: (Composite => Any)*)(parent: Composite) = {
    setups.foreach(setup => setup(parent))
    parent
  }

  object Assignments {
    private def nothing: Nothing = error("this method is not meant to be called")

    def url(implicit ev: Nothing) = nothing
    def url_=[T <: { def setUrl(data: String) }](data: String) =
      (subject: T) => subject.setUrl(data)
  }
}