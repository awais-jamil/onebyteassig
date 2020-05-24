package com.example.onebyteassign.networkLayer

import com.google.firebase.firestore.*

class OBFirebaseService(
    val collectionReference: CollectionReference,
    val query: Query
) {

    private var listener: ListenerRegistration? = null

    //pagination
    private var lastVisible: DocumentSnapshot? = null

    /// Returns auto document id for the new document
    fun autoDocumentId(): DocumentReference {
        return collectionReference.document()
    }

    /// Pull records in pagination form
    ///
    /// - Parameters:
    ///   - pageSize: size of the page
    ///   - completion: retruns the results when done
    fun fetch(
        pageSize:Long,
        completion: (result: List<DocumentChange>?, exception: Exception?)-> Unit
    ){

        //fetch
        fetchQuery(pageSize).get()
            .addOnSuccessListener {querySnapshot ->

                completion(querySnapshot.documentChanges, null)
            }
            .addOnFailureListener {exception ->

                completion(null, exception)
            }
    }

    /// Add the instance to same fileprovider as query
    ///
    /// - Parameters:
    ///   - data: element to saved
    ///   - completion: callback for completion
    fun add(
        data: HashMap<String, String>,
        completion:(exception: Exception?)-> Unit
    ){
        collectionReference.add(data)
            .addOnSuccessListener {

                completion(null)
            }
            .addOnFailureListener {exception ->

                completion(exception)
            }
    }

    /// Updates one specific object in collection
    ///
    /// - Parameters:
    ///   - element: element
    ///   - completion: callback for completion
    fun update(
        documentId:String,
        data:HashMap<String, Any>,
        completion:(exception: Exception?)-> Unit
    ) {

        val docRef = collectionReference.document(documentId)
        docRef.set(data)
            .addOnSuccessListener {

                completion(null)
            }
            .addOnFailureListener {exception ->

                completion(exception)
            }
    }

    /// Deletes the specific element from the collection
    ///
    /// - Parameters:
    ///   - docId: document uid
    ///   - completion: callback for completion
    fun delete(
        documentId:String,
        completion:(exception: Exception?)-> Unit
    ){

        val docRef = collectionReference.document(documentId)
        docRef.delete()
            .addOnSuccessListener {

                completion(null)
            }
            .addOnFailureListener {exception ->

                completion(exception)
            }
    }

    /// Gets one specific object in collection
    ///
    /// - Parameters:
    ///   - element: element
    ///   - completion: callback for completion
    fun fetch(
        documentId:String,
        completion:(result: DocumentSnapshot?, exception: Exception?)-> Unit
    ){

        val docRef = collectionReference.document(documentId)

        docRef.get()
            .addOnSuccessListener {

                completion(it, null)
            }
            .addOnFailureListener {exception ->

                completion(null, exception)
            }
    }

    /// Construct the query based on page size and last record
    ///
    /// - Parameters:
    ///   - pageSize: size of the page
    /// - Returns: updated Query
    private fun fetchQuery(pageSize:Long): Query {


        lastVisible?.let {

            //we have a previous record
            return query.limit(pageSize).startAfter(it)

        }?: run {

            //if this is first page
            return query.limit(pageSize)
        }

    }

    /// Subscribe  changes
    ///
    /// - Parameters:
    ///   - added: added callback
    ///   - modified: modified callback
    ///   - removed: removed callback
    fun subscribeForUpdates(
        limit: Long,
        onAdded: (changes: List<DocumentChange>)-> Unit,
        onModified: (changes: List<DocumentChange>)-> Unit,
        onRemoved: (changes: List<DocumentChange>)-> Unit,
        onException: (exception: Exception)-> Unit
    ){

        listener = query.limit(limit)
            .addSnapshotListener(
                EventListener { snapshot, firebaseFirestoreException ->

                    if(firebaseFirestoreException != null)
                        onException(firebaseFirestoreException)

                    else {

                        snapshot?.let {querySnapshot ->

                            val added = querySnapshot.documentChanges.filter {

                                it.type == DocumentChange.Type.ADDED
                            }

                            if(added.isNotEmpty())
                                onAdded(added)

                            val modified = querySnapshot.documentChanges.filter {

                                it.type == DocumentChange.Type.MODIFIED
                            }

                            if(modified.isNotEmpty())
                                onModified(modified)

                            val removed = querySnapshot.documentChanges.filter {

                                it.type == DocumentChange.Type.REMOVED
                            }

                            if(removed.isNotEmpty())
                                onRemoved(removed)
                        }

                    }
                }


        )
    }

    /// stop listening to message form this room
    fun unsubscribe(){

        listener?.remove()
    }

    /*
    /// Converts rooms data to rooms
    ///
    /// - Parameter documentChanges: document changes
    /// - Returns: parsed rooms
    fileprivate func parseObjects(from documentChanges:[DocumentChange]) -> [Element]{
        let rooms = CSChatSDKUtil.decodeElements(from: documentChanges) as [Element]
        return rooms
    }*/
}