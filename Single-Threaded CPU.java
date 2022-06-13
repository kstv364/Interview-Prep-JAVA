// 	You are given n​​​​​​ tasks labeled from 0 to n - 1 represented by a 2D integer array tasks, where tasks[i] = [enqueueTimei, processingTimei] means that the i​​​​​​th​​​​ task will be available to process at enqueueTimei and will take processingTimei to finish processing.

// You have a single-threaded CPU that can process at most one task at a time and will act in the following way:

// If the CPU is idle and there are no available tasks to process, the CPU remains idle.
// If the CPU is idle and there are available tasks, the CPU will choose the one with the shortest processing time. If multiple tasks have the same shortest processing time, it will choose the task with the smallest index.
// Once a task is started, the CPU will process the entire task without stopping.
// The CPU can finish a task then start a new one instantly.
    
    public int[] getOrder(int[][] tasks) {
		PriorityQueue<Task> taskQueue = new PriorityQueue<>((x, y) -> x.enqueueTime - y.enqueueTime);
		PriorityQueue<Task> availableQueue = new PriorityQueue<>((x, y) -> x.processingTime != y.processingTime
				? x.processingTime - y.processingTime : x.index - y.index);

		int idx = 0;

		for (int[] task : tasks) {
			taskQueue.add(new Task(idx++, task[0], task[1]));
		}

		int currTime = taskQueue.peek().enqueueTime;
		int[] result = new int[tasks.length];
		while (!taskQueue.isEmpty() || !availableQueue.isEmpty()) {

			while (!taskQueue.isEmpty() && taskQueue.peek().enqueueTime <= currTime) {
				availableQueue.add(taskQueue.poll());
			}

			if (!availableQueue.isEmpty()) {
				Task curr = availableQueue.poll();
				currTime += curr.processingTime;
				result[idx++] = curr.index;
			} else {
				// fast forward time
				currTime = taskQueue.peek().enqueueTime;
			}

		}
		return result;

	}

	class Task {
		int index, enqueueTime, processingTime;

		public Task(int index, int enqueueTime, int processingTime) {
			this.index = index;
			this.enqueueTime = enqueueTime;
			this.processingTime = processingTime;
		}
	}