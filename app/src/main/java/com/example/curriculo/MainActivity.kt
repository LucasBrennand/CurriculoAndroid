import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter
import com.example.curriculo.R
import com.example.curriculo.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.backgroundphone),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Surface(color = MaterialTheme.colorScheme.background.copy(alpha = 0.1f)) {
            if (shouldShowOnboarding) {
                OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
            } else {
                Greetings(
                    name = "Your Name",
                    textsWithExpanded = listOf(
                        "Quem sou eu" to "Sou um estudante de Ciências da Computação na UNICAP. Trabalhei com computadores minha vida inteira, sendo assim, acabei optando por seguir um carreira de programação.\n" +
                                "Tenho ampla experiência principalmente com Java, mas também trabalho com Javascript, C++ e outros.\n" +
                                "Estou sempre querendo conhecer novas linguagens e aprender mais sobre a área de tecnologia.\n" +
                                "Estou á procura de novos desafios, onde posso aplicar meu conhecimento e gerar resultados tanto para mim quanto para os outros.",
                        "Formação Acadêmica" to "Universidade Católica de Pernambuco (2020 - 2025)",
                        "Competências" to
                                "Front-end:\n" +
                                "HTML, CSS, Javascript" +
                                "\n" +
                                "Back-end:\n" +
                                "Java, C++, Processing\n" +
                                "\n" +
                                "Outras Habilidades:\n" +
                                "Hardware de Computadores, Excel, Linux\n" +
                                "\n" +
                                "Idiomas:\n" +
                                "Inglês, Português",
                        "Contato" to "+55 (81)99569-7350\n" +
                                "lucasbrennand.barbosa@gmail.com" +
                                "\n" +
                                "@lucasbbrennand"
                    )
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Bem Vindo",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        )
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked,
        ) {
            Text("Continue")
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    name: String = "Lucas Brennand Barbosa Chiaperini",
    textsWithExpanded: List<Pair<String, String>> = List(4) { "Text $it" to "Expanded text $it" }
) {
    Column(modifier = modifier.fillMaxSize()) {
        Profile(Modifier.size(200.dp), name = name)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = textsWithExpanded) { (text, expandedText) ->
                Greeting(text = text, expandedText = expandedText)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 32.dp)
                .wrapContentWidth(align = Alignment.End),
            horizontalArrangement = Arrangement.End
        ) {
            @Composable
            fun shareButton(
                onShareClicked: () -> Unit,
                modifier: Modifier = Modifier
            ) {
                val context = LocalContext.current
                val url = "LucasBrennandApp"
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, url)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                IconButton(
                    onClick = {
                        onShareClicked()
                        context.startActivity(shareIntent)
                    },
                    modifier = modifier
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_share_25),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 32.dp)
                    .wrapContentWidth(align = Alignment.End),
                horizontalArrangement = Arrangement.End
            ) {
                shareButton(
                    onShareClicked = {
                        // Handle share click
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .padding(end = 8.dp)
                )
                @Composable
                fun WhatsAppShareButton(
                    onShareClicked: () -> Unit,
                    modifier: Modifier = Modifier
                ) {
                    val context = LocalContext.current
                    val url = "LucasBrennandApp"
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, url)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    IconButton(
                        onClick = {
                            onShareClicked()
                            context.startActivity(shareIntent)
                        },
                        modifier = modifier
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.whatsapp2),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                WhatsAppShareButton(
                    onShareClicked = {
                    },
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
private fun Greeting(text: String, expandedText: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(text = text, expandedText = expandedText)
    }
}

@Composable
private fun CardContent(text: String, expandedText: String) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3494E6),
                        Color(0xFFEC6EAD)
                    )
                ),
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                )
                if (expanded) {
                    Text(
                        text = expandedText,
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        color = Color.White
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Filled.ArrowBack else Filled.ArrowDropDown,
                    contentDescription = if (expanded) {
                        stringResource(androidx.compose.material3.R.string.expanded)
                    } else {
                        stringResource(androidx.compose.material3.R.string.collapsed)
                    }
                )
            }
        }
    }
}

@Composable
private fun Profile(modifier: Modifier, name: String) {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        imageUri.value
    )
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.eu), contentDescription = null,
                modifier = Modifier
                    .wrapContentSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "Lucas Brennand Barbosa Chiaperini",
            textAlign = TextAlign.Center,
            modifier = Modifier.width(150.dp),
            color = Color.White
        )
    }
}
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}