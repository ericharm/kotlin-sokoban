package com.ericharm
import java.io.File
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.input.KeyType

class Level (val width: Int, val height: Int) {
    constructor(width: Int, height: Int, descriptor: String): this(width, height) {
        fromDescriptor(descriptor)
    }

    constructor(width: Int, height: Int, descriptor: File): this(width, height) {
        val contents = descriptor.readText()
        fromDescriptor(contents)
    }

    object hero : Entity(Point(0, 0)) {
        override val character = '@'
    }

    // use copy() method to make this immutable
    var entities: List<Entity> = listOf(hero)

    // update this to be static, calculate width and height from descriptor
    fun fromDescriptor(descriptor: String) {
        val rows = descriptor.split("\n").filter { it.length > 0 }
        for (y: Int in 0..rows.size - 1) {
            for (x: Int in 0..rows[y].length - 1) {
                // add one to account for border,
                // better come up with something nicer than this
                if (rows[y][x] == '0') entities += Boulder(Point(x + 1, y + 1))
                if (rows[y][x] == '@') hero.moveTo(x + 1, y + 1)
            }
        }
    }

    fun render(graphics: TextGraphics) {
        entities.forEach { it.render(graphics) }
    }

    fun handleInput(key: KeyType) {
        val direction = hashMapOf(
            KeyType.ArrowDown to Point(0, 1), KeyType.ArrowUp to Point(0, -1),
            KeyType.ArrowLeft to Point(-1, 0), KeyType.ArrowRight to Point(1, 0)
        )[key]
        if (direction != null) hero.moveThroughLevel(this, direction.x, direction.y)
    }

    fun update() { }
}
