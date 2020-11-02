package com.example
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal

fun main(args: Array<String>) {
    val terminal = DefaultTerminalFactory().createTerminal()
    val screen = TerminalScreen(terminal)
    screen.startScreen()

    // Create panel to hold components
    val panel = Panel()
    panel.setLayoutManager(GridLayout(2))

    panel.addComponent(Label("Forename"))
    panel.addComponent(TextBox())

    panel.addComponent(Label("Surname"))
    panel.addComponent(TextBox())

    panel.addComponent(EmptySpace(TerminalSize(0,0)))
    panel.addComponent(Button("Submit"));

    // Create window to hold the panel
    val window = BasicWindow()
    window.setComponent(panel)

    // Create gui and start gui
    val gui = MultiWindowTextGUI(screen, DefaultWindowManager(), EmptySpace(TextColor.ANSI.BLUE))
    gui.addWindowAndWait(window)
}
