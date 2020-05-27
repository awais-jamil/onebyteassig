package com.example.onebyteassign.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onebyteassign.models.Categories
import com.example.onebyteassign.models.Movie
import com.example.onebyteassign.models.OBCurrentUser
import com.example.onebyteassign.networkLayer.OBFirebaseService
import com.google.firebase.firestore.FirebaseFirestore


class HomeViewModel : ViewModel() {

    var user =  MutableLiveData<OBCurrentUser>()

    var movies =  MutableLiveData<ArrayList<Movie>>()
    var categories =  MutableLiveData<ArrayList<Categories>>()
    var moviesDataLoaded =  MutableLiveData<Boolean>()


    //invitations listener
    lateinit var moviesListener: OBFirebaseService
    lateinit var categoriesListener: OBFirebaseService

    fun fetchAndSubscribeToCattegory() {

        val firestore = FirebaseFirestore.getInstance()
        val path = "categories/"
        val collection = firestore.collection(path)
        val query = collection
        categoriesListener = OBFirebaseService(collection, query)
        categories.value = ArrayList()
        categoriesListener.fetch(
            5, completion = {result, exception ->

                exception?.let {

                }

                result?.let {

                    val categories = ArrayList<Categories>()

                    it.forEach {documentChange ->

                        val categorie = documentChange.document.toObject(Categories::class.java)
                        categories.add(categorie)
                    }

                    this.categories.value = categories
                }
            }
        )


    }


    fun fetchAndSubscribeToMovies() {

        val firestore = FirebaseFirestore.getInstance()

        val path = "movies/"
        val collection = firestore.collection(path)
        val query = collection
        moviesListener = OBFirebaseService(collection, query)

        movies.value = ArrayList()

        subscribeToUpdates()
    }

    private fun subscribeToUpdates(){

        moviesListener.subscribeForUpdates(
            25,
            onAdded = {

                val movies = ArrayList<Movie>()

                it.forEach {documentChange ->

                    val movie = documentChange.document.toObject(Movie::class.java)
                    movie.id = documentChange.document.id
                    movies.add(movie)
                }

                addMovies(movies)
            },
            onModified = {

                it.forEach { documentChange ->


                }
            },
            onRemoved = {

                val movies = ArrayList<Movie>()

                it.forEach { documentChange ->

                    val movie = documentChange.document.toObject(Movie::class.java)
                    movie.id = documentChange.document.id
                    movies.add(movie)
                }

                removeMovies(movies)
            },
            onException = {

                it.printStackTrace()
            }
        )
    }

    private fun addMovies(movies: ArrayList<Movie>){

        val wrappers = ArrayList<Movie>();

        movies.forEach { movie ->

            wrappers.add(movie)
        }

        if(wrappers.size > 0) {

            val currentMovies = this.movies.value!!

            currentMovies.addAll(0, wrappers)

            this.movies.value = currentMovies
            moviesDataLoaded.value = true
        }
    }

    private fun removeMovies(movies:ArrayList<Movie>) {

        //current messages
        val currentMovies = this.movies.value!!

        movies.forEach { movie ->

            if (currentMovies.contains(movie)) {
                currentMovies.remove(movie)
            }
        }

        this.movies.value = currentMovies
        moviesDataLoaded.value = true
    }


}