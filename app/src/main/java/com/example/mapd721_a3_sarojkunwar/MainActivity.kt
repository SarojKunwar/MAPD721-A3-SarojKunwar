package com.example.mapd721_a3_sarojkunwar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapd721_a3_sarojkunwar.ui.theme.MAPD721A3SarojKunwarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721A3SarojKunwarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "start") {
                        composable("start") {
                            StartComposable(navController = navController)
                        }
                        composable("exitanimation") {
                            ExitAnimation(navController = navController)
                        }
                        composable("clock") {
                            Clock(navController = navController)
                        }
                        composable("scale") {
                            Scale(navController = navController)
                        }
                        composable("rocket") {
                            Rocket(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun StartComposable(navController: NavController){
    Text(text = "Welcome to Animation Homepage", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ){
        MyButton(text = "Transition Button", onClick = { navController.navigate("rocket") })
        MyButton(text = "Scale Button", onClick = { navController.navigate("scale") })
        MyButton(text = "Infinite Button", onClick = { navController.navigate("clock") })
        MyButton(text = "Enter and Exit Button", onClick = { navController.navigate("exitanimation") })
    }
}
@Composable
fun Rocket(navController: NavController){
    val animationState = remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        RocketStart(
            isRocketEnabled = animationState.value,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            rocketSize = 200.dp
        )

        LaunchButton(animationState = animationState.value) {
            animationState.value = !animationState.value
        }
    }
}

@Composable
fun Scale(navController: NavController, modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        var isScaled by remember { mutableStateOf(false) }
        val transition = updateTransition(targetState = isScaled, label = "scale")

        val scale by transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = if (targetState) 4000 else 3800)
            },
            label = "scale factor"
        ) { targetState ->
            if (targetState) 1.5f else 1f
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bat),
                contentDescription = "Image",
                modifier = Modifier
                    .size(150.dp)
                    .scale(scale)
                    .clickable(onClick = { isScaled = !isScaled })
            )
            Button(
                onClick = { isScaled = !isScaled },
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(text = if (isScaled) "Shrink Image" else "Scale Image")
            }
        }
    }
}


@Composable
fun Clock(navController: NavController){
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ){

    }
}
@Composable
fun ExitAnimation(navController: NavController){
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ){

    }
}
@Composable
fun LaunchButton(
    animationState: Boolean,
    onToggleAnimationState: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        if (animationState) {
            Button(
                onClick = onToggleAnimationState,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(text = "STOP")
            }
        } else {
            Button(
                onClick = onToggleAnimationState,
            ) {
                Text(text = "LAUNCH")
            }
        }
    }
}
@Composable
fun RocketStart(
    isRocketEnabled: Boolean,
    maxWidth: Dp,
    maxHeight: Dp,
    rocketSize: Dp
) {
    val painter: Painter
    val modifier: Modifier

    if (isRocketEnabled) {
        val infiniteTransition = rememberInfiniteTransition()

        val engineState = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    easing = FastOutLinearInEasing
                )
            )
        )

        val positionState = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 4000,
                    easing = LinearEasing
                )
            )
        )

        painter = if (engineState.value <= 0.5f) {
            painterResource(id = R.drawable.rocket_launch)
        } else {
            painterResource(id = R.drawable.rocket_launch)
        }

        modifier = Modifier.offset(
            x = (maxWidth - rocketSize) * positionState.value,
            y = (maxHeight - rocketSize) - (maxHeight - rocketSize) * positionState.value
        )
    } else {
        painter = painterResource(id = R.drawable.rocket_launch)
        modifier = Modifier.offset(y = maxHeight - rocketSize)
    }

    Image(
        painter = painter,
        contentDescription = "Rocket",
        modifier = modifier
            .size(rocketSize)
    )
}


@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color.White,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
    ) {
        Text(text = text, color = textColor)
    }
}



