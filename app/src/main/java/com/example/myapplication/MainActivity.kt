package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility // Import AnimatedVisibility
import androidx.compose.animation.fadeIn // Import fade-in animation
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(
                    topBar = {
                        OfficeTopAppBar(title = "Another Day at the Office")
                    },
                    content = { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFF0F8FF))
                                .padding(padding)
                        ) {
                            OfficeUI()
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfficeTopAppBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF0F8FF),
            //titleContentColor = Color.Transparent
        )
    )
}

@Composable
fun OfficeUI() {
    var neighborVisible by remember { mutableStateOf(false) }
    var breakroomVisible by remember { mutableStateOf(false) }
    var bathroomVisible by remember { mutableStateOf(false) }

    val neighborImage = R.drawable.neighbor
    val breakroomImage = R.drawable.breakroom
    val bathroomImage = R.drawable.otherbathroom

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OfficeImageButton(
            text = "Neighbor",
            iconResource = R.drawable.peeking_peering_over,
            isVisible = neighborVisible,
            onToggle = { neighborVisible = !neighborVisible },
            imageResource = neighborImage,
            bottomText = "Charlie can never mind his own business..."
        )
        Spacer(modifier = Modifier.height(32.dp))
        OfficeImageButton(
            text = "Breakroom",
            iconResource = R.drawable.break_time_icon,
            isVisible = breakroomVisible,
            onToggle = { breakroomVisible = !breakroomVisible },
            imageResource = breakroomImage,
            bottomText = "I should really start going out for lunch."
        )
        Spacer(modifier = Modifier.height(32.dp))
        OfficeImageButton(
            text = "Bathroom",
            iconResource = R.drawable.toilet,
            isVisible = bathroomVisible,
            onToggle = { bathroomVisible = !bathroomVisible },
            imageResource = bathroomImage,
            bottomText = "I hope Charlie doesn't find me in here."
        )
    }

    AnimatedVisibility(
        visible = neighborVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FullscreenImagePopup(
            imageResource = neighborImage,
            onDismiss = { neighborVisible = false },
            bottomText = "Charlie can never mind his own business..."
        )
    }

    AnimatedVisibility(
        visible = breakroomVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FullscreenImagePopup(
            imageResource = breakroomImage,
            onDismiss = { breakroomVisible = false },
            bottomText = "I should really start going out for lunch."
        )
    }

    AnimatedVisibility(
        visible = bathroomVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FullscreenImagePopup(
            imageResource = bathroomImage,
            onDismiss = { bathroomVisible = false },
            bottomText = "I hope Charlie doesn't find me in here."
        )
    }
}

@Composable
fun OfficeImageButton(
    text: String,
    iconResource: Int,
    isVisible: Boolean,
    onToggle: () -> Unit,
    imageResource: Int,
    bottomText: String
) {
    Button(
        onClick = onToggle,
        modifier = Modifier.size(width = 200.dp, height = 100.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF808080),
            contentColor = Color.White
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(iconResource),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, fontSize = 20.sp, color = Color.White)
        }
    }
}

@Composable
fun FullscreenImagePopup(imageResource: Int, onDismiss: () -> Unit, bottomText: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                painter = painterResource(imageResource),
                contentDescription = null
            )
            Text(
                text = bottomText,
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(8.dp)
            )
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(48.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray, contentColor = Color.White),
                contentPadding = PaddingValues(0.dp) // Remove default content padding
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Close Image", tint = Color.White)
            }

        }
    }
}