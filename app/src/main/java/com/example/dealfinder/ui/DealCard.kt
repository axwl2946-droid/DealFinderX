package com.example.dealfinder.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dealfinder.R // Assuming you'll add a dummy image in drawable
import com.example.dealfinder.ui.theme.DealFinderTheme

data class Deal(
    val id: String,
    val imageUrl: Int, // For simplicity, using Int drawable resource for now
    val title: String,
    val oldPrice: Double,
    val newPrice: Double,
    val storeName: String,
    val storeLogoUrl: Int, // For simplicity, using Int drawable resource for now
    val temperature: Int,
    val commentsCount: Int
)

@Composable
fun DealCard(deal: Deal, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = deal.imageUrl),
            contentDescription = deal.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient Overlay for Text Readability
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(180.dp) // Adjust height as needed
            .align(Alignment.BottomCenter)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                    startY = 0f, // Start gradient from 0% height of this Box
                    endY = Float.POSITIVE_INFINITY // End at 100% height
                )
            )
        )

        // Deal Info (Bottom Left)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp, end = 80.dp) // Leave space for action buttons
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = deal.storeLogoUrl),
                    contentDescription = deal.storeName,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White) // Optional: add background for logo visibility
                        .padding(4.dp) // Optional: padding inside circle
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = deal.storeName, style = MaterialTheme.typography.titleSmall, color = Color.White)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = deal.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)) {
                        append("${deal.newPrice} ₽")
                    }
                    withStyle(style = SpanStyle(
                        color = Color.LightGray,
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 16.sp
                    )) {
                        append(" ${deal.oldPrice} ₽")
                    }
                }
            )
        }

        // Action Buttons (Bottom Right)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Upvote Button (Temperature)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { /* TODO: Handle upvote */ }) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Upvote", tint = Color.White, modifier = Modifier.size(32.dp))
                }
                Text(
                    text = "${deal.temperature}°",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { /* TODO: Handle downvote */ }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Downvote", tint = Color.White, modifier = Modifier.size(32.dp))
                }
            }

            // Comments Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { /* TODO: Show comments */ }) {
                    Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Comments", tint = Color.White, modifier = Modifier.size(32.dp))
                }
                Text(text = "${deal.commentsCount}", color = Color.White, fontSize = 16.sp)
            }

            // Share Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { /* TODO: Share deal */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White, modifier = Modifier.size(32.dp))
                }
                Text(text = "Поделиться", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DealCardPreview() {
    DealFinderTheme {
        val dummyDeal = Deal(
            id = "1",
            imageUrl = R.drawable.deal_image_placeholder,
            title = "Умные часы Xiaomi Watch S1 Active GL, лунный белый",
            oldPrice = 15999.0,
            newPrice = 9999.0,
            storeName = "M.Видео",
            storeLogoUrl = R.drawable.store_logo_placeholder,
            temperature = 256,
            commentsCount = 123
        )
        DealCard(dummyDeal)
    }
}