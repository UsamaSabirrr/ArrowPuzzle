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
                Scaffold(modifier = Modifier.fillMaxSize()) {it->
                    GameBoard()
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
data class Path(val startRow:Int,val startCol:Int,val direciton:List<Direction>)

val samplePath = Path(startRow = 2, startCol = 2, direciton = listOf( Direction.RIGHT,
    Direction.UP,
    Direction.RIGHT
    ))


fun gridToPixel(row:Int,col:Int,spacing:Float):Offset{
    return Offset(col*spacing+spacing,row*spacing+spacing)
}

@Composable
fun GameBoard(){
    val rows = 10
    val cols = 10
    val spacing = 80f
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(400.dp).background(color = Color.Yellow)) {
            for (row in 0 until rows){
                for (col in 0 until cols){
                    val x = col*spacing+spacing
                    val y = row*spacing+spacing
                    drawCircle(color = Color.Black, radius = 4f,center=Offset(x,y))
                }
            }
            var currentRow = samplePath.startRow
            var currentCol = samplePath.startCol

            samplePath.direciton.forEach {direction->
                val nextCoordinates = when(direction){
                    Direction.UP->Pair<Int,Int>(currentRow-1,currentCol)
                    Direction.DOWN->Pair<Int,Int>(currentRow+1,currentCol)
                    Direction.LEFT->Pair<Int,Int>(currentRow,currentCol-1)
                    Direction.RIGHT->Pair<Int,Int>(currentRow,currentCol+1)
                }
                val currentOffset = gridToPixel(currentRow,currentCol,spacing)
                val nextOffset = gridToPixel(nextCoordinates.first,nextCoordinates.second,spacing)
                currentRow = nextCoordinates.first
                currentCol = nextCoordinates.second
                drawLine(Color.Blue,currentOffset,nextOffset,strokeWidth = 10f,cap = StrokeCap.Round)
            }
        }
    }

}
