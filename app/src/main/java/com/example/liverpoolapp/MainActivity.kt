package com.example.liverpoolapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.liverpoolapp.ui.theme.LiverpoolAppTheme

// Define the routes
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Players : Screen("players")
    object PlayerDetails : Screen("playerdetails")
    object Trophies : Screen("trophies")
    object Favourites : Screen("favourites")
}

// create navigation items for the bottombar
data class NaviItem(
    var label:String,
    val icon: ImageVector,
    val screen: Screen
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiverpoolAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBarWithIcons(navController)
                    }
                ) { innerPadding ->
                    MainFunction(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

val favourite_players = mutableStateListOf("false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false")

//
// Player Info
//
val players = listOf("Alisson Becker", "Caoimhin Kelleher",
    "Virgil Van Dijk", "Andrew Robertson", "Trent Alexander-Arnold", "Ibrahima Konate",
    "Alexis Mac Allister", "Dominik Szobozlai", "Ryan Gravenberch",
    "Mohamed Salah", "Diogo Jota", "Luis Diaz", "Cody Gakpo", "Darwin Nunez")
val player_details = listOf("Alisson Becker is a Brazilian international player. He joined Liverpool in July 2018. He has played 271 times for the main team. Although he is a goalkeeper he has scored 1 goal for Liverpool which was vital to Liverpool getting into champions league that season. In total Alisson has won 8 trophies during his time at Liverpool, the two most important being the Premier League in 2019/2020 and the Champions League in 2018/2019.",
                            "Caoimhin Kelleher is an Irish international player. He came up through the Liverpool Academy. He has played 49 times for the main team. In total Kelleher has won 8 trophies during his time at Liverpool, the two most important being the Premier League in 2019/2020 and the Champions League in 2018/2019.",
                            "Virgil Van Dijk is a Dutch international player. He joined Liverpool in January 2018. He has played 279 games for the main team. He has scored 24 goals for Liverpool. In total Van Dijk has won 8 trophies during his time at Liverpool, the two most important being the Premier League in 2019/2020 and the Champions League in 2018/2019.",
                            "Andrew Robertson is a Scottish international player. He joined Liverpool in July 2017. He has played 306 times for the main team. He has scored 11 goals for Liverpool. In total Robertson has won 8 trophies during his time at Liverpool, the two most important being the Premier League in 2019/2020 and the Champions League in 2018/2019.",
                            "Trent Alexander-Arnold is an English international player. He came up through the Liverpool Academy. He has played 319 times for the main team. He has scored 19 goals for Liverpool. In total Trent has won 8 trophies during his time at Liverpool, the two most important being the Premier League in 2019/2020 and the Champions League in 2018/2019.",
                            "Ibrahima Konate is a French international player. He joined Liverpool in July 2021. He has played 99 times for the main team. He has scored 5 goals for Liverpool. Konate has won 4 trophies for liverpool the biggest achievement being winning the FA cup and the EFL cup in the 2021/2022 season.",
                            "Alexis Mac Allister is an Argentine international player. He joined Liverpool in June 2023. He has played 56 times for the main team. He has scored 8 goals for Liverpool. Mac Allister has only won 1 trophy which was the EFL Cup in the 2023/2024 season.",
                            "Dominik Szobozlai is a Hungarian international player. He joined Liverpool in July 2023. He has played 54 times for the main team. He has scored 8 goals for Liverpool. Szoboszlai has only won 1 trophy which was the EFL Cup in the 2023/2024 season.",
                            "Ryan Gravenberch is a Dutch international player. He joined Liverpool in September 2023. He has socred 4 goals for Liverpool. Gravenberch has only won 1 trophy which was the EFL Cup in the 2023/2024 season.",
                            "Mohamed Salah is an Egyptian international player. He joined Liverpool in July 2017. He has played 359 times for the main team. He has scored 217 goals for Liverpool. In total Salah has won 8 trophies during his time at Liverpool, the two most important being the Premier League in 2019/2020 and the Champions League in 2018/2019.",
                            "Diogo Jota is a Portuguese international player. He joined Liverpool in September 2020. He has played 154 times for the main team. He has scored 60 goals for Liverpool. Jota has won 4 trophies for liverpool the biggest achievement being winning the FA cup and the EFL cup in the 2021/2022 season.",
                            "Luis Diaz is a Columbian international player. He joined Liverpool in January 2022. He has played 107 times for the main team. He has scored 29 goals for Liverpool. Diaz has won 4 trophies for liverpool the biggest achievement being winning the FA cup and the EFL cup in the 2021/2022 season.",
                            "Cody Gakpo is a Dutch international player. He joined Liverpool in January 2023. He has played 89 times for the main team. He has scored 25 goals for Liverpool. Gakpo has only won 1 trophy which was the EFL Cup in the 2023/2024 season.",
                            "Darwin Nunez is an Uruguayan international player He joined Liverpool in June 2022. He has played 103 time for the main team. He has scored 34 goals for Liverpool. Nunez has won 2 trophies for Liverpool")

val position = listOf("GK", "GK", "CB", "LB", "RB", "CB", "CM", "CAM", "CM", "RW", "ST", "LW", "LW", "ST")
val position_name = listOf("Goalkeeper", "Goalkeeper", "Central Defender", "Left Back", "Right Back", "Central Defender", "Central Midfielder", "Central Attacking Midfielder", "Central Midfielder", "Right Winger", "Striker", "Left Winger", "Left Winger", "Striker")
val age = listOf(32, 25, 33, 30, 25, 25, 25, 23, 22, 32, 27, 27, 25, 25)
val foot = listOf("Right", "Right", "Right", "Left", "Right", "Right", "Right", "Right", "Right", "Left", "Right", "Right", "Right", "Right")
val shirt_number = listOf(1, 62, 4, 26, 66, 5, 10, 8, 38, 11, 20, 7, 18, 9)
val country = listOf("Brazil", "Ireland", "Netherlands", "Scotland", "England", "France", "Argentina", "Hungary", "Netherlands", "Egypt", "Portugal", "Columbia", "Netherlands", "Uruguay")
val face_pics = listOf(R.drawable.alisson, R.drawable.kelleher, R.drawable.vandijk, R.drawable.andy, R.drawable.trent, R.drawable.konate, R.drawable.macca, R.drawable.dom, R.drawable.ryan, R.drawable.salah, R.drawable.jota, R.drawable.diaz, R.drawable.gakpo, R.drawable.darwin)
val country_pics = listOf(R.drawable.bra, R.drawable.irl,R.drawable.ned, R.drawable.sco, R.drawable.eng, R.drawable.fra, R.drawable.arg, R.drawable.hun, R.drawable.ned, R.drawable.egy, R.drawable.por, R.drawable.col, R.drawable.ned, R.drawable.uru)
val market_values = listOf("€35M", "€22M", "€35M", "€35M", "€72M", "€50M", "€70M", "€80M", "€30M", "€57M", "€55M", "€70M", "€55M", "€75M")

//
// Trophy Info
//
val trophy_names = listOf("Premier League", "Championship", "Champions League", "Europa League", "Community Shield", "Fa Cup", "EFL Cup", "UEFA Super Cup", "FIFA Club World Cup")
val times_trophy_was_won = listOf("(19)", "(4)", "(6)", "(3)", "(16)", "(8)", "(10)", "(4)", "(1)")
val trophy_years = listOf("2019/2020 - 1989/1990 - 1987/1988 - 1985/1986 - 1983-1984 - 1982/1983 - 1981/1982 - 1979/1980 - 1978/1979 - 1976/1977 - 1975/1976 - 1972/1973 - 1965/1966 - 1963/1964 - 1946/1947 - 1922/1923 - 1921/1922 - 1905/1906 - 1900/1901",
    "1961/1962 - 1904/1905 - 1895/1896 - 1893/1894",
    "2018/2019 - 2004/2005 - 1983/1984 - 1980/1981 - 1977/1978 - 1976/1977",
    "2000/2001 - 1975/1976 - 1972/1973",
    "2022/2023 - 2006/2007 - 2001/2002 - 1990/1991 - 1989/1990 - 1988/1989 - 1986/1987 - 1982/1983 - 1980/1981 - 1979/1980 - 1977/1978 - 1976/1977 - 1974/1975 - 1966/1967 - 1965/1966 - 1964/1965",
    "2021/2022 - 2005/2006 - 2000/2001 - 1991/1992 - 1988/1989 - 1985/1986 - 1973/1974 - 1964/1965",
    "2023/2024 - 2021/2022 - 2011/2012 - 2002/2003 - 2000/2001 - 1994/1995 - 1983/1984 - 1982/1983 - 1981/1982 - 1980/1981",
    "2019/2020 - 2005/2006 - 2001/2002 - 1977/1978",
    "2019 Qatar"
)

var current_player = 0


//
// Bottom
// Bar
// Navigation
//
@Composable
fun BottomBarWithIcons(navController: NavHostController){
    val navItemList = listOf(
        NaviItem(label ="Home", icon = Icons.Default.Home, screen = Screen.Home),
        NaviItem(label ="Players", icon = Icons.Default.AccountCircle, screen = Screen.Players),
        NaviItem(label ="Trophies", icon = Icons.Default.Star, screen = Screen.Trophies),
        NaviItem(label ="Favourites", icon = Icons.Default.Favorite, screen = Screen.Favourites)
    )

    var selectedIndex by remember { mutableStateOf(0) }

    BottomAppBar(
        containerColor = Color.DarkGray
    ) {
        NavigationBar(
            containerColor = Color.DarkGray,
        ) {
            navItemList.forEachIndexed{index, item ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick={
                        selectedIndex = index
                        // navigate to item screen
                        if(navController.currentDestination?.route != item.screen.route){
                            navController.navigate(item.screen.route){
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                    },
                    icon = {Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selectedIndex == index) Color.Black else Color.White
                    )},
                    label ={Text(
                        text = item.label,
                        color = Color.White
                    )}
                )
            }
        }
    }
}


//
// Home
// Page
// Screen
//
@Composable
fun HomeScreen() {
    LazyColumn(modifier = Modifier.padding(vertical = 20.dp)) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.liverpool),
                                contentDescription = "Player Face Image",
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Liverpool FC",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = "Est. 1892",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Manager",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.slot),
                                contentDescription = "Manager Face Image",
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Arne Slot",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = "Age  46",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Liverpool is one of the most successful clubs in England, some say the most successful.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "All Time Top Goal Scorer",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.rush),
                                contentDescription = "Player Face Image",
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Ian Rush",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = "346",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "All Time Top Assists",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.stevie),
                                contentDescription = "Player Face Image",
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Steven Gerrard",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = "92",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "All Time Top Appearances",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ian),
                                contentDescription = "Player Face Image",
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Ian Callaghan",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = "857",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Current Starting XI",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Row for Forwards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.jota),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp) // Set width to make it square
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Row for Fowards/Attacking Midfielders
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gakpo),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.dom),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.salah),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Row for Midfielders
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.macca),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ryan),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Row for Defenders
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.andy),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.vandijk),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.konate),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.trent),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Row for Goalkeeper
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.alisson),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                    }
                }
            }
        }
    }
}


//
// Players
// Page
// Screen
//
@Composable
fun PlayersScreen(navController: NavHostController) {

    LazyColumn(modifier = Modifier.padding(vertical = 20.dp)) {
        itemsIndexed(players) { index, player ->
            PlayersScreenBoxes(player, index, navController)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
        }
    }
}

@Composable
fun PlayersScreenBoxes(item: String, number: Int, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
        ),
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = face_pics[number]),
                        contentDescription = "Player Face Image",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                val imageOne = painterResource(id = R.drawable.unfilledstar)
                val imageTwo = painterResource(id = R.drawable.filledstar)

                Image(
                    painter = if (favourite_players[number] == "true") imageTwo else imageOne,
                    contentDescription = "Star Image",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable(onClick = {
                            favourite_players[number] = if (favourite_players[number] == "false") "true" else "false"
                        })
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = country_pics[number]),
                    contentDescription = "Player Country Image",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = country[number],
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    navController.navigate(Screen.PlayerDetails.route)
                    current_player = number
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = "View Player")
                }
            }

        }
    }
}


//
// Players
// Details
// Screen
//
@Composable
fun PlayersDetailsScreen() {
    LazyColumn(modifier = Modifier.padding(vertical = 55.dp)) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Player",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = face_pics[current_player]),
                            contentDescription = "Player Face Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = players[current_player],
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = country_pics[current_player]),
                            contentDescription = "Player Country Image",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = country[current_player],
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Position",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = position[current_player],
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = position_name[current_player],
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Extra Info",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Age = " + age[current_player],
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }

                        Text(
                            text = "Foot = " + foot[current_player],
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Shirt No. = " + shirt_number[current_player],
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }

                        Text(
                            text = "Market Value = " + market_values[current_player],
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Backstory",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = player_details[current_player],
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}


//
// Trophy
// History
// Screen
//
@Composable
fun TrophiesScreen() {
    LazyColumn(
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        itemsIndexed(trophy_names) { num, trophy ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = trophy,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Winner " + times_trophy_was_won[num],
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = trophy_years[num],
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}


//
// Favourite
// Players
// Screen
//
@Composable
fun FavouriteScreen(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        itemsIndexed(favourite_players) { num, player ->
            if (player == "true") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = face_pics[num]),
                                    contentDescription = "Player Face Image",
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = players[num],
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            val imageOne = painterResource(id = R.drawable.unfilledstar)
                            val imageTwo = painterResource(id = R.drawable.filledstar)

                            Image(
                                painter = if (favourite_players[num] == "true") imageTwo else imageOne,
                                contentDescription = "Star Image",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable(onClick = {
                                        favourite_players[num] = if (favourite_players[num] == "false") "true" else "false"
                                    })
                            )

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = country_pics[num]),
                                contentDescription = "Player Country Image",
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = country[num],
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = {
                                navController.navigate(Screen.PlayerDetails.route)
                                current_player = num
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ),) {
                                Text(text = "View Player")
                            }
                        }

                    }
                }
            }
        }
    }
}


//
//
// Main
//
//
@Composable
fun MainFunction(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier.background(color = Color.Black)
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Players.route) {
            PlayersScreen(navController)
        }
        composable(Screen.PlayerDetails.route) {
            PlayersDetailsScreen()
        }
        composable(Screen.Trophies.route) {
            TrophiesScreen()
        }
        composable(Screen.Favourites.route) {
            FavouriteScreen(navController)
        }
    }
}