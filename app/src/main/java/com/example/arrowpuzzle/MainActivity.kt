package com.example.arrowpuzzle

import android.R
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arrowpuzzle.ui.theme.ArrowPuzzleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArrowPuzzleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameScreen()
                }
            }
        }
    }
}

enum class Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT
}
data class Cell(val row: Int, val col: Int,val direction: Direction)

val dummyBoard = listOf(
    listOf(
        Cell(0, 0, Direction.RIGHT),
        Cell(0, 1, Direction.DOWN),
        Cell(0, 2, Direction.LEFT),
        Cell(0, 3, Direction.UP),
    ),
//    listOf(
//        Cell(1, 0, Direction.UP),
//        Cell(1, 1, Direction.RIGHT),
//        Cell(1, 2, Direction.DOWN),
//        Cell(1, 3, Direction.LEFT),
//    ),
//    listOf(
//        Cell(2, 0, Direction.LEFT),
//        Cell(2, 1, Direction.UP),
//        Cell(2, 2, Direction.RIGHT),
//        Cell(2, 3, Direction.DOWN),
//    )
)

@Composable
fun GameScreen(){
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        dummyBoard.forEach { row->Row{
            row.forEach { cell->
                ArrowCell(cell)
            }
        }}
    }
}

@Composable
fun ArrowCell(cell: Cell){
    Box(modifier = Modifier.size(80.dp).padding(4.dp),contentAlignment = Alignment.Center){
        Box(modifier = Modifier.background(Color.Yellow).fillMaxSize(),)
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArrow(cell.direction)
        }
    }
}

fun DrawScope.drawArrow(direction: Direction) {

    val padding = 10f

    val centerX = size.width / 2
    val centerY = size.height / 2

    val start: Offset
    val end: Offset

    when (direction) {

        Direction.RIGHT -> {

            start = Offset(
                padding,
                centerY
            )

            end = Offset(
                size.width - padding,
                centerY
            )
        }

        Direction.LEFT -> {

            start = Offset(
                size.width - padding,
                centerY
            )

            end = Offset(
                padding,
                centerY
            )
        }

        Direction.UP -> {

            start = Offset(
                centerX,
                size.height - padding
            )

            end = Offset(
                centerX,
                padding
            )
        }

        Direction.DOWN -> {

            start = Offset(
                centerX,
                padding
            )

            end = Offset(
                centerX,
                size.height - padding
            )
        }
    }

    drawLine(
        color = Color.Blue,
        start = start,
        end = end,
        strokeWidth = 8f,
        cap = StrokeCap.Round
    )
}