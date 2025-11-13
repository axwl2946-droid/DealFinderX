package com.example.dealfinder.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dealfinder.R
import kotlinx.coroutines.launch
import com.example.dealfinder.ui.Deal
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.input.KeyboardOptions

// Sealed class to define navigation routes
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Главная", Icons.Default.Home)
    object Search : Screen("search", "Поиск", Icons.Default.Search)
    object Add : Screen("add", "Добавить", Icons.Default.AddCircle)
    object Alerts : Screen("alerts", "Оповещения", Icons.Default.Notifications)
    object Profile : Screen("profile", "Профиль", Icons.Default.Person)
}

val items = listOf(
    Screen.Home,
    Screen.Search,
    Screen.Add,
    Screen.Alerts,
    Screen.Profile
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { HomeTabsScreen() }
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Add.route) { AddScreen() }
            composable(Screen.Alerts.route) { AlertsScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}

// Component for vertically swiping deals (TikTok style)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DealsVerticalPagerScreen(deals: List<Deal>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { deals.size })

    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) {
        page ->
        DealCard(deal = deals[page], modifier = Modifier.fillMaxSize())
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeTabsScreen() {
    val dummyDeals = remember {
        listOf(
            Deal(
                id = "1",
                imageUrl = R.drawable.deal_image_placeholder,
                title = "Умные часы Xiaomi Watch S1 Active GL, лунный белый",
                oldPrice = 15999.0,
                newPrice = 9999.0,
                storeName = "M.Видео",
                storeLogoUrl = R.drawable.store_logo_placeholder,
                temperature = 256,
                commentsCount = 123
            ),
            Deal(
                id = "2",
                imageUrl = R.drawable.deal_image_placeholder,
                title = "Смартфон Samsung Galaxy S24 Ultra 12/512GB, титановый серый",
                oldPrice = 149999.0,
                newPrice = 129999.0,
                storeName = "DNS",
                storeLogoUrl = R.drawable.store_logo_placeholder,
                temperature = 180,
                commentsCount = 89
            ),
            Deal(
                id = "3",
                imageUrl = R.drawable.deal_image_placeholder,
                title = "Ноутбук ASUS ROG Strix G15 G513RC-HN013W, R7-6800H, 16GB, 512GB SSD, RTX 3050",
                oldPrice = 119999.0,
                newPrice = 99999.0,
                storeName = "Ситилинк",
                storeLogoUrl = R.drawable.store_logo_placeholder,
                temperature = 300,
                commentsCount = 201
            ),
            Deal(
                id = "4",
                imageUrl = R.drawable.deal_image_placeholder,
                title = "Игровая приставка Sony PlayStation 5 Slim 825GB, CFI-2000A",
                oldPrice = 65000.0,
                newPrice = 59990.0,
                storeName = "МВидео",
                storeLogoUrl = R.drawable.store_logo_placeholder,
                temperature = 450,
                commentsCount = 350
            ),
             Deal(
                id = "5",
                imageUrl = R.drawable.deal_image_placeholder,
                title = "Беспроводные наушники Apple AirPods Pro 2 с MagSafe Charging Case (USB-C)",
                oldPrice = 25000.0,
                newPrice = 19990.0,
                storeName = "Re:Store",
                storeLogoUrl = R.drawable.store_logo_placeholder,
                temperature = 220,
                commentsCount = 95
            )
        )
    }

    val tabs = listOf("Лучшее", "Скидки", "Для вас")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            selectedTabIndex = it
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            page ->
            when (page) {
                0 -> DealsVerticalPagerScreen(deals = dummyDeals)
                1 -> DealsVerticalPagerScreen(deals = dummyDeals)
                2 -> DealsVerticalPagerScreen(deals = dummyDeals)
            }
        }
    }
}

@Composable
fun SearchScreen() {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Поиск скидок", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Что ищем?") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Фильтры:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Filter for Categories
        Button(onClick = { /* TODO: Open category filter */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Категории")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Filter for Stores
        Button(onClick = { /* TODO: Open store filter */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Магазины")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Filter for Minimum Temperature
        Button(onClick = { /* TODO: Open min temperature filter */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Минимальный градус")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Filter for Price Range
        Button(onClick = { /* TODO: Open price range filter */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Диапазон цен")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* TODO: Perform Search with filters */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Применить поиск")
        }
    }
}

@Composable
fun AddScreen() {
    var link by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("Выбрать категорию") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Added for scrollability
        horizontalAlignment = Alignment.CenterHorizontally // Centered horizontally
    ) {
        Text(text = "Добавить новую скидку", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("Ссылка на товар") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Название товара") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { newValue ->
                if (newValue.matches(Regex("^\\d*\\.?\\d*")) || newValue.isEmpty()) {
                    price = newValue
                }
            },
            label = { Text("Цена (руб.)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* TODO: Open category picker */ }, modifier = Modifier.fillMaxWidth()) {
            Text(category)
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { /* TODO: Submit new deal */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Добавить скидку")
        }
    }
}

@Composable
fun AlertsScreen() {
    var keyword by rememberSaveable { mutableStateOf("") }
    var minTemperature by rememberSaveable { mutableStateOf("") }

    val dummyAlerts = remember { mutableStateListOf("PS5 > 100°", "Xiaomi телефон > 50°", "Наушники") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Мои оповещения", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            label = { Text("Ключевое слово") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = minTemperature,
            onValueChange = { newValue ->
                if (newValue.matches(Regex("^\\d*")) || newValue.isEmpty()) {
                    minTemperature = newValue
                }
            },
            label = { Text("Минимальный градус") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* TODO: Add new alert */
                           if (keyword.isNotBlank()) {
                               val alertText = if (minTemperature.isNotBlank()) {
                                   "$keyword > $minTemperature°"
                               } else {
                                   keyword
                               }
                               dummyAlerts.add(alertText)
                               keyword = ""
                               minTemperature = ""
                           }
                         }, modifier = Modifier.fillMaxWidth()) {
            Text("Добавить оповещение")
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Активные оповещения:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(dummyAlerts) {
                alert ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Text(text = alert, modifier = Modifier.padding(12.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    val dummySavedDeals = remember { listOf(
        Deal("p1", R.drawable.deal_image_placeholder, "Монитор Samsung Odyssey G7", 60000.0, 45000.0, "Ситилинк", R.drawable.store_logo_placeholder, 150, 30),
        Deal("p2", R.drawable.deal_image_placeholder, "Клавиатура HyperX Alloy FPS Pro", 8000.0, 5500.0, "DNS", R.drawable.store_logo_placeholder, 80, 15)
    ) }
    val dummyAlerts = remember { listOf("Ноутбук", "Мышь игровая > 200°") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Мой профиль", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Сохраненные скидки", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) { // Limited height for preview
            items(dummySavedDeals) {
                deal ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { /* TODO: Open deal details */ },
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = deal.imageUrl), contentDescription = null, modifier = Modifier.size(48.dp).clip(RoundedCornerShape(4.dp)))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = deal.title, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(text = "${deal.newPrice} ₽ в ${deal.storeName}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Мои оповещения", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.heightIn(max = 150.dp)) { // Limited height for preview
            items(dummyAlerts) {
                alert ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { /* TODO: Edit alert */ },
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Text(text = alert, modifier = Modifier.padding(12.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { /* TODO: Open settings */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Настройки")
        }
    }
}