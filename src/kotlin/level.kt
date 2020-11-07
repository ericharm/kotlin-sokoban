package com.ericharm
import java.io.File
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.input.KeyType

class Level (val width: Int, val height: Int) {
    companion object {
        fun fromDescriptor(descriptor: String): Level {
            val rows = descriptor.split("\n").filter { it.length > 0 }
            val width = if (rows.size > 0) rows[0].length else 0
            val level = Level(width, rows.size)
            level.generate(descriptor)
            return level
        }

        fun fromDescriptor(descriptor: File): Level {
            val contents = descriptor.readText()
            return Level.fromDescriptor(contents)
        }
    }

    object hero : Entity(Point(0, 0)) {
        override val character = '@'

        override open fun onCollidesWith(entities: List<Entity>, level: Level, vector: Point): Boolean {
            entities.forEach {
                if (it is Boulder && it.moveThroughLevel(level, vector)) {
                    move(vector.x, vector.y)
                    return true
                }
            }
            return false
        }
    }

    var entities: List<Entity> = listOf(hero)

    fun generate(descriptor: String) {
        val rows = descriptor.split("\n").filter { it.length > 0 }
        for (y: Int in 0..rows.size - 1) {
            for (x: Int in 0..rows[y].length - 1) {
                // add one to account for border, come up with something nicer than this
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
        if (direction != null) hero.moveThroughLevel(this, Point(direction.x, direction.y))
    }

    fun update() {}
}
