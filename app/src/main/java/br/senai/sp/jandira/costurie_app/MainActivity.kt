package br.senai.sp.jandira.costurie_app

import ProfileScreen
import ProfileViewedScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.costurie_app.models_private.User
import br.senai.sp.jandira.costurie_app.screens.chats.ChatListScreen
import br.senai.sp.jandira.costurie_app.screens.chats.ChatScreen
import br.senai.sp.jandira.costurie_app.screens.chats.PictureScreen
import br.senai.sp.jandira.costurie_app.screens.editProfile.EditProfileScreen
import br.senai.sp.jandira.costurie_app.screens.editProfile.TagsEditProfileScreen
import br.senai.sp.jandira.costurie_app.screens.editPublication.EditPublicationScreen
import br.senai.sp.jandira.costurie_app.screens.expandedPublication.ExpandedPublicationScreen
import br.senai.sp.jandira.costurie_app.screens.explore.ExploreScreen
import br.senai.sp.jandira.costurie_app.screens.home.HomeScreen
import br.senai.sp.jandira.costurie_app.screens.loading.LoadingScreen
import br.senai.sp.jandira.costurie_app.screens.login.LoginScreen
import br.senai.sp.jandira.costurie_app.screens.main.MainScreen
import br.senai.sp.jandira.costurie_app.screens.password.PasswordScreen
import br.senai.sp.jandira.costurie_app.screens.personalization.AboutScreen
import br.senai.sp.jandira.costurie_app.screens.personalization.ChangeEmailScreen
import br.senai.sp.jandira.costurie_app.screens.settings.ChangePasswordScreen
import br.senai.sp.jandira.costurie_app.screens.personalization.DescriptionScreen
import br.senai.sp.jandira.costurie_app.screens.personalization.LocationScreen
import br.senai.sp.jandira.costurie_app.screens.personalization.NameScreen
import br.senai.sp.jandira.costurie_app.screens.personalization.ProfilePicScreen
import br.senai.sp.jandira.costurie_app.screens.personalization.TagSelectScreen
import br.senai.sp.jandira.costurie_app.screens.settings.TermsAndConditionsScreen
import br.senai.sp.jandira.costurie_app.screens.personalization.TypeProfileScreen
import br.senai.sp.jandira.costurie_app.screens.profile.ProfileListScreen
import br.senai.sp.jandira.costurie_app.screens.register.RegisterScreen
import br.senai.sp.jandira.costurie_app.screens.services.ServicesScreen
import br.senai.sp.jandira.costurie_app.screens.settings.HelpAndSupportScreen
import br.senai.sp.jandira.costurie_app.screens.settings.SettingsScreen
import br.senai.sp.jandira.costurie_app.screens.settings.YourAccountScreen
import br.senai.sp.jandira.costurie_app.screens.tradePassword.TradePasswordScreen
import br.senai.sp.jandira.costurie_app.screens.validationCode.ValidationCodeScreen
import br.senai.sp.jandira.costurie_app.service.chat.ChatClient
import br.senai.sp.jandira.costurie_app.service.chat.view_model.ChatViewModel
import br.senai.sp.jandira.costurie_app.service.chat.view_model.ViewModelID
import br.senai.sp.jandira.costurie_app.sqlite_repository.UserRepositorySqlite
import br.senai.sp.jandira.costurie_app.ui.theme.Costurie_appTheme
import br.senai.sp.jandira.costurie_app.viewModel.PasswordResetViewModel
import br.senai.sp.jandira.costurie_app.viewModel.TagPublicationViewModel
import br.senai.sp.jandira.costurie_app.viewModel.TagsViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserTagViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel
import br.senai.sp.jandira.costurie_app.viewModel.UserViewModel2
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Costurie_appTheme {
                val navController = rememberAnimatedNavController()
                val viewModelPassword = viewModel<PasswordResetViewModel>()
                val viewModelUser = viewModel<UserViewModel>()
                val viewModelUser2 = viewModel<UserViewModel2>()
                val viewModelTags = viewModel<TagsViewModel>()
                val viewModelUserTags = viewModel<UserTagViewModel>()
                val viewModelTagsPublication = viewModel<TagPublicationViewModel>()
                val localStorage: Storage = Storage()
                val chatViewModel = viewModel<ChatViewModel>()
                val IdViewModel = viewModel<ViewModelID>()
                val client = ChatClient()
                val socket = client.getSocket()
                AnimatedNavHost(
                    navController = navController,
                    startDestination = "validationCode"
                )

                {
                    composable(route = "main") { MainScreen(navController = navController) }
                    composable(route = "register") {
                        RegisterScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope
                        )
                    }
                    composable(route = "login") {
                        LoginScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope
                        )
                    }
                    composable(route = "password") {
                        PasswordScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            viewModelPassword
                        )
                    }
                    composable(route = "validationCode") {
                        ValidationCodeScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            viewModelPassword
                        )
                    }
                    composable(route = "tradePassword") {
                        TradePasswordScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            viewModelPassword
                        )
                    }
                    composable(route = "loading") {
                        LoadingScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope
                        )
                    }
                    composable(route = "home") {
                        HomeScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            viewModelUser2,
                            chatViewModel
                        )
                    }
                    composable(route = "explore") {
                        ExploreScreen(
                            navController = navController,
                            localStorage = localStorage
                        )
                    }
//                    composable(route = "publish") { PublishScreen(navController = navController, lifecycleScope = lifecycleScope, localStorage = localStorage)}
//                    composable(route = "expandedComment") { ExpandedCommentScreen(nav) }


                    composable(route = "expandedPublication") {
                        val context = LocalContext.current

                        val dadaUser = UserRepositorySqlite(context).findUsers()

                        var array = User()

                        var data = ""

                        if (dadaUser.isNotEmpty()) {
                            array = dadaUser[0]


                            data = array.id.toString()
                        }

                        Log.e("eu mandei", "id: ${data}")

                        val client1 = ChatClient()
                        client1.connect(data.toInt())
                        val socket1 = client1.getSocket()
                        ExpandedPublicationScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            viewModel = viewModelTagsPublication,
                            localStorage = localStorage,
                            viewModelId = IdViewModel,
                            socket = socket1,
                            client = client,
                            chatViewModel = chatViewModel
                        )
                    }
                    composable(route = "services") {
                        ServicesScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            categories = emptyList(),
                            filterings = emptyList(),
                            viewModelUserTags = viewModelUserTags,
                            localStorage = localStorage
                        )
                    }
                    composable(route = "profile") {
                        ProfileScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            viewModel = viewModelUser2,
                            localStorage = localStorage
                        )
                    }
                    composable(route = "profileViewed") {

                        val context = LocalContext.current

                        val dadaUser = UserRepositorySqlite(context).findUsers()

                        var array = User()

                        var data = ""

                        if (dadaUser.isNotEmpty()) {
                            array = dadaUser[0]


                            data = array.id.toString()
                        }

                        Log.e("eu mandei", "id: ${data}")

                        val client1 = ChatClient()
                        client1.connect(data.toInt())
                        val socket1 = client1.getSocket()

                        ProfileViewedScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            viewModel = viewModelUser2,
                            localStorage = localStorage,
                            chatViewModel = chatViewModel,
                            client = client,
                            socket = socket1,
                            viewModelId = IdViewModel
                        )
                    }
                    composable(route = "profileList") {
                        ProfileListScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            profiles = emptyList(),
                            viewModel = viewModelUser,
                            viewModelUserTags = viewModelUserTags,
                            localStorage = localStorage
                        )
                    }
                    composable(route = "editProfile") {
                        EditProfileScreen(
                            lifecycleScope = lifecycleScope,
                            navController = navController,
                            viewModel = viewModelUser2,
                            localStorage = localStorage
                        )
                    }
                    composable(route = "tagsEditProfile") {
                        TagsEditProfileScreen(
                            lifecycleScope = lifecycleScope,
                            navController = navController,
                            viewModelUser = viewModelUser,
                            viewModelTags = viewModelTags,
                            localStorage = localStorage
                        )
                    }
                    composable(route = "editPublication") {
                        EditPublicationScreen(
                            lifecycleScope = lifecycleScope,
                            navController = navController,
                            localStorage = localStorage,
                            viewModelTag = viewModelTagsPublication
                        )
                    }

                    //telas de chat
                    composable(route = "chatList") {
                        val context = LocalContext.current

                        //val dadaUser = UserRepository(context).findUsers()
                        val dadaUser = UserRepositorySqlite(context).findUsers()

                        var array = User()

                        var data = ""

                        if (dadaUser.isNotEmpty()) {
                            array = dadaUser[0]

                            data = array.id.toString()
                        }
                        client.connect(data.toInt())

                        ChatListScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            localStorage = localStorage,
                            client = client,
                            socket = socket,
                            chatViewModel = chatViewModel,
                            idUsuario = data.toInt()
                        )
                    }
                    composable(route = "chat") {
                        val context = LocalContext.current

                        val dadaUser = UserRepositorySqlite(context).findUsers()

                        var array = User()

                        var data = ""
                        if (dadaUser.isNotEmpty()) {
                            array = dadaUser[0]


                            data = array.id.toString()

                            Log.e("eu mandei", "id: ${data}")


                            val client = ChatClient()
                            client.connect(data.toInt())
                            val socket = client.getSocket()
                            ChatScreen(
                                lifecycleScope = lifecycleScope,
                                navController = navController,
                                client = client,
                                socket = socket,
                                chatViewModel = chatViewModel,
                                idUsuario = data.toInt()
                            )
                        }
                    }

                    composable(route = "pictureScreen") {
                        val context = LocalContext.current

                        val dadaUser = UserRepositorySqlite(context).findUsers()

                        var array = User()

                        var data = ""
                        if (dadaUser.isNotEmpty()) {
                            array = dadaUser[0]


                            data = array.id.toString()

                            Log.e("eu mandei", "id: ${data}")


                            val client = ChatClient()
                            client.connect(data.toInt())
                            val socket = client.getSocket()
                            PictureScreen(
                                navController = navController,
                                chatViewModel = chatViewModel,
                                client = client,
                                idUsuario = data.toInt(),
                                socket = socket
                            )
                        }
                    }//
                    //telas de configuracões
                    composable(route = "settings") {
                        SettingsScreen(
                            lifecycleScope = lifecycleScope,
                            navController = navController,
                            localStorage = localStorage
                        )
                    }
                    composable(route = "yourAccount") {
                        YourAccountScreen(
                            lifecycleScope = lifecycleScope,
                            navController = navController
                        )
                    }
                    composable(route = "changeEmail") {
                        ChangeEmailScreen(
                            navController = navController,
                            localStorage = localStorage,
                            lifecycleScope = lifecycleScope
                        )
                    }
                    composable(route = "changePassword") {
                        ChangePasswordScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope,
                            localStorage = localStorage
                        )
                    }
                    composable(route = "about") { AboutScreen(navController = navController) }
                    composable(route = "termsAndConditions") {
                        TermsAndConditionsScreen(
                            navController = navController
                        )
                    }
                    composable(route = "helpAndSupport") {
                        HelpAndSupportScreen(
                            navController = navController,
                            localStorage = localStorage
                        )
                    }

                    //telas de personalização
                    composable(route = "name") {
                        NameScreen(
                            navController = navController,
                            localStorage
                        )
                    }
                    composable(route = "foto") {
                        ProfilePicScreen(
                            navController = navController,
                            localStorage,
                            lifecycleScope = lifecycleScope
                        )
                    }
                    composable(route = "description") {
                        DescriptionScreen(
                            navController = navController,
                            localStorage,
                            lifecycleScope = lifecycleScope
                        )
                    }
                    composable(route = "location") {
                        LocationScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope
                        )
                    }
                    composable(route = "profileType") {
                        TypeProfileScreen(
                            navController = navController,
                            lifecycleScope = lifecycleScope
                        )
                    }
                    composable(route = "tagSelection") {
                        TagSelectScreen(
                            lifecycleScope = lifecycleScope,
                            navController = navController
                        )
                    }
                }
            }

        }
    }
}
