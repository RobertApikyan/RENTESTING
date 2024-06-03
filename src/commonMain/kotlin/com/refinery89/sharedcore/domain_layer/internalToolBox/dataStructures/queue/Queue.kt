package com.refinery89.sharedcore.domain_layer.internalToolBox.dataStructures.queue

/**
 * Contract interface for a typical queue data structure
 * @param T the type of the elements in the queue
 * @property count Int the number of elements in the queue
 * @property isEmpty Boolean true if the queue is empty
 */
internal interface Queue<T> : Iterable<T>
{
	val count: Int
	
	val isEmpty: Boolean
		get() = count == 0
	
	/**
	 * Enqueues an element for ever in this order
	 * @param element T
	 * @return Boolean
	 */
	fun enqueue(element: T): Boolean
	
	/**
	 * Dequeues an element and re-queues it to the end of the queue
	 * @return T?
	 */
	fun dequeue(): T?
	
	/**
	 * Peeks at the next element in the queue
	 * @return T?
	 */
	fun peek(): T?
	
	/**
	 * Clears the queue
	 * @return Unit
	 */
	fun clear()
}

/**
 * A queue that uses an array list as the backing data structure
 */
internal class ArrayQueue<T> : Queue<T>
{
	private val list = arrayListOf<T>()
	
	override val count: Int
		get() = list.size
	
	/**
	 * Peeks at the next element in the queue
	 * @return T?
	 */
	override fun peek(): T? = list.getOrNull(0)
	
	/**
	 * Enqueues an element for ever in this order
	 * @param element T
	 * @return Boolean
	 */
	override fun enqueue(element: T): Boolean
	{
		list.add(element)
		return true
	}
	
	/**
	 * Dequeues an element and re-queues it to the end of the queue
	 * @return T?
	 */
	override fun dequeue(): T?
	{
		if (isEmpty)
			return null
		
		return list.removeAt(0)
	}
	
	/**
	 * @suppress
	 */
	override fun toString(): String
	{
		return list.toString()
	}
	
	/**
	 * Clears the queue
	 * @return Unit
	 */
	override fun clear()
	{
		list.clear()
	}
	
	/**
	 * Returns an iterator for the queue
	 * @return Iterator<T>
	 */
	override fun iterator(): Iterator<T>
	{
		return list.iterator()
	}
}

/**
 * This queue will requeue the last dequeued item to the end of the queue
 * so this list is infinite in terms of the number of times you can call [dequeue]
 * once you add something to it you can never remove unless you remove everything
 */
internal class ReQueueQueue<T> : Queue<T>
{
	private val normalQueue = ArrayQueue<T>()
	override val count: Int
		get() = normalQueue.count
	
	/**
	 * Enqueues an element for ever in this order
	 * @param element T
	 * @return Boolean
	 */
	override fun enqueue(element: T): Boolean
	{
		normalQueue.enqueue(element)
		return true
	}
	
	/**
	 * Dequeues an element and re-queues it to the end of the queue
	 * @return T?
	 */
	override fun dequeue(): T?
	{
		if (isEmpty)
			return null
		
		return normalQueue.dequeue().also {
			if (it != null) normalQueue.enqueue(it)
		}
	}
	
	/**
	 * Peeks at the next element in the queue
	 * @return T?
	 */
	override fun peek(): T?
	{
		return normalQueue.peek()
	}
	
	/**
	 * Clears the queue
	 * @return Unit
	 */
	override fun clear()
	{
		normalQueue.clear()
	}
	
	/**
	 * Returns an iterator for the queue
	 * @return Iterator<T>
	 */
	override fun iterator(): Iterator<T>
	{
		return normalQueue.iterator()
	}
	
	
}