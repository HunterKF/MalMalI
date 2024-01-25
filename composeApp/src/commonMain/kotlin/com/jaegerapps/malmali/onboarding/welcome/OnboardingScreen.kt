package com.jaegerapps.malmali.onboarding.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.onboarding.welcome.components.OnboardingContainer
import com.jaegerapps.malmali.onboarding.welcome.components.PagerIndicator
import com.jaegerapps.malmali.onboarding.welcome.components.SkipAndNextButton
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun WelcomeScreen(
    component: WelcomeScreenComponent
) {
    var page by remember {
        mutableIntStateOf(1)
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.weight(0.2f))
        val modifier = Modifier.weight(1f)
        when (page) {
            1 -> {
                OnboardingContainer_Welcome(modifier)
            }

            2 -> {
                OnboardingContainer_Grammar(modifier)
            }

            3 -> {
                OnboardingContainer_Chat(modifier)
            }

            4 -> {
                OnboardingContainer_Wherever(modifier)
            }
        }

        PagerIndicator(
            currentPage = page,
            numberOfPages = 4
        )
        Spacer(Modifier.height(24.dp))
        SkipAndNextButton(
            onSkip = {

            },
            onNext = {
                if (page < 4) {
                    page++
                } else {
                    page = 1
                }
            }
        )
    }
}

@Composable
fun OnboardingContainer_Welcome(
    modifier: Modifier = Modifier,
) {
    OnboardingContainer(
        modifier = modifier,
        icon = MR.images.cat_icon,
        title = stringResource(MR.strings.onboarding_welcome_title),
        text = stringResource(MR.strings.onboarding_welcome_content)
    )
}

@Composable
fun OnboardingContainer_Grammar(
    modifier: Modifier = Modifier,
) {
    OnboardingContainer(
        modifier = modifier,
        icon = MR.images.onboarding_grammar,
        title = stringResource(MR.strings.onboarding_grammar_title),
        text = stringResource(MR.strings.onboarding_grammar_content)
    )
}

@Composable
fun OnboardingContainer_Chat(
    modifier: Modifier = Modifier,
) {
    OnboardingContainer(
        modifier = modifier,
        icon = MR.images.onboarding_chat,
        title = stringResource(MR.strings.onboarding_chat_title),
        text = stringResource(MR.strings.onboarding_chat_content)
    )
}

@Composable
fun OnboardingContainer_Wherever(
    modifier: Modifier = Modifier,
) {
    OnboardingContainer(
        modifier = modifier,
        icon = MR.images.onboarding_typing,
        title = stringResource(MR.strings.onboarding_on_the_go_title),
        text = stringResource(MR.strings.onboarding_on_the_go_content)
    )
}