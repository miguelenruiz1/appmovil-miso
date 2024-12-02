package com.misw4203.vinyls.network

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.misw4203.vinyls.models.Collector
import com.misw4203.vinyls.models.Performer
import com.misw4203.vinyls.models.Album
import com.misw4203.vinyls.models.CollectorAlbum
import com.misw4203.vinyls.models.CollectorDetail
import org.json.JSONArray
import org.json.JSONObject
import com.misw4203.vinyls.models.Track
import com.misw4203.vinyls.models.Comment
import com.misw4203.vinyls.models.PerformerDetails
import com.misw4203.vinyls.models.createAlbum


class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "https://backvynils-q6yc.onrender.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    private fun <T> parseJson(jsonString: String, clazz: Class<T>): T {
        val gson = Gson()
        val newObject = gson.fromJson(jsonString, clazz)
        return newObject
    }

    fun getCollectors(
        onComplete: (resp: List<Collector>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest("collectors",
                { response ->
                    Log.d("tagb", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Collector>()
                    var item: JSONObject? = null
                    for (i in 0 until resp.length()) {
                        item = resp.getJSONObject(i)
                        list.add(
                            i,
                            parseJson(item.toString(), Collector::class.java)
                        )
                    }
                    onComplete(list)
                },
                {
                    onError(it)
                })
        )
    }

    fun getCollectorDetail(
        collectorId: Int,
        onComplete: (resp: CollectorDetail) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest("collectors/${collectorId}",
                { response ->
                    Log.d("CollectorDetail", response)
                    onComplete(parseJson(response, CollectorDetail::class.java))
                },
                {
                    onError(it)
                })
        )
    }

    fun getAlbums(
        onComplete: (resp: List<Album>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest("albums",
                { response ->
                    Log.d("Albums", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            Album(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                cover = item.getString("cover"),
                                releaseDate = item.getString("releaseDate"),
                                description = item.getString("description"),
                                genre = item.getString("genre"),
                                recordLabel = item.getString("recordLabel")
                            )
                        )
                    }
                    onComplete(list)
                },
                {
                    onError(it)
                })
        )
    }

    fun getPerformers(
        onComplete: (resp: List<Performer>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest("musicians",
                { response ->
                    Log.d("tagb", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Performer>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i,
                            Performer(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                image = item.getString("image"),
                                birthday = item.getString("birthDate"),
                                description = item.getString("description")
                            )
                        )
                    }
                    onComplete(list)
                },
                {
                    onError(it)
                })
        )
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    private fun putRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.PUT,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    fun getAlbumDetails(
        albumId: Int,
        onComplete: (Album) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest("albums/$albumId",
                { response ->
                    val item = JSONObject(response)
                    val album = Album(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        releaseDate = item.getString("releaseDate"),
                        description = item.getString("description"),
                        genre = item.getString("genre"),
                        recordLabel = item.getString("recordLabel"),
                        tracks = item.getJSONArray("tracks").let { tracksJson ->
                            val tracksList = mutableListOf<Track>()
                            for (i in 0 until tracksJson.length()) {
                                val track = tracksJson.getJSONObject(i)
                                tracksList.add(
                                    Track(
                                        id = track.getInt("id"),
                                        name = track.getString("name"),
                                        duration = track.getString("duration")
                                    )
                                )
                            }
                            tracksList
                        },
                        performers = item.getJSONArray("performers").let { performersJson ->
                            val performersList = mutableListOf<PerformerDetails>()
                            for (i in 0 until performersJson.length()) {
                                val performer = performersJson.getJSONObject(i)
                                performersList.add(
                                    PerformerDetails(
                                        performerId = performer.getInt("id"),
                                        name = performer.getString("name"),
                                        image = performer.getString("image"),
                                        description = performer.getString("description"),
                                        birthday = performer.getString("birthDate")
                                    )
                                )
                            }
                            performersList
                        },
                        comments = item.getJSONArray("comments").let { commentsJson ->
                            val commentsList = mutableListOf<Comment>()
                            for (i in 0 until commentsJson.length()) {
                                val comment = commentsJson.getJSONObject(i)
                                commentsList.add(
                                    Comment(
                                        id = comment.getInt("id"),
                                        description = comment.getString("description"),
                                        rating = comment.getInt("rating")
                                    )
                                )
                            }
                            commentsList
                        }
                    )
                    onComplete(album)
                },
                { onError(it) }
            )
        )
    }

    fun createAlbum(
        album: createAlbum,
        onComplete: (createAlbum) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val body = JSONObject().apply {
            put("name", album.name)
            put("cover", album.cover)
            put("releaseDate", album.releaseDate)
            put("description", album.description)
            put("genre", album.genre)
            put("recordLabel", album.recordLabel)
        }

        requestQueue.add(
            postRequest(
                path = "albums",
                body = body,
                responseListener = { response ->
                    Log.d("CreateAlbum", "Álbum creado exitosamente: ${response.toString()}")
                    val createdAlbum = createAlbum(
                        name = response.getString("name"),
                        cover = response.getString("cover"),
                        releaseDate = response.getString("releaseDate"),
                        description = response.getString("description"),
                        genre = response.getString("genre"),
                        recordLabel = response.getString("recordLabel")
                    )
                    onComplete(createdAlbum)
                },
                errorListener = { error ->
                    if (error.networkResponse != null) {
                        val statusCode = error.networkResponse.statusCode
                        val errorData = String(error.networkResponse.data, Charsets.UTF_8)
                        Log.e("CreateAlbum", "Error al crear el álbum: $statusCode - $errorData")
                    } else {
                        Log.e("CreateAlbum", "Error al crear el álbum: ${error.message}")
                    }
                    onError(error)
                }
            )
        )
    }

    fun createComment(
        albumId: Int,
        collectorId: Int,
        comment: Comment,
        onComplete: (Comment) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val body = JSONObject().apply {
            put("description", comment.description)
            put("rating", comment.rating)
            put("collector", {
                val collectorObject = JSONObject()
                collectorObject.put("id", collectorId)
                collectorObject
            })
        }

        requestQueue.add(
            postRequest(
                path = "albums/$albumId/comments",
                body = body,
                responseListener = { response ->
                    Log.d("CreateComment", "Comentario creado exitosamente: ${response.toString()}")
                    val createdComment = Comment(
                        description = response.getString("description"),
                        rating = response.getInt("rating")
                    )
                    onComplete(createdComment)
                },
                errorListener = { error ->
                    if (error.networkResponse != null) {
                        val statusCode = error.networkResponse.statusCode
                        val errorData = String(error.networkResponse.data, Charsets.UTF_8)
                        Log.e(
                            "CreateComment",
                            "Error al crear el comentario: $statusCode - $errorData"
                        )
                    } else {
                        Log.e("CreateComment", "Error al crear el comentario: ${error.message}")
                    }
                    onError(error)
                }
            )
        )



    }

}