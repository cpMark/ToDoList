package learn.cp.todolist.task.query;

/**
 * function：
 * author by cpMark
 * create on 2018/12/18.
 */
public class ToDoItemBean {


    /**
     * toDoId : 3
     * taskStatus : 0
     * userId : 1
     * createTaskTime : 2018-12-14T05:06:34.000+0000
     * expectedFinishTaskTime : 2018-12-14T05:06:34.000+0000
     * finishTaskTime : null
     * priority : 1
     * title : 待办1
     * content : todocontent
     */

    /**
     * 待办任务id
     */
    private int toDoId;

    /**
     *  待办任务状态
     */
    private int taskStatus;

    /**
     *  用户id
     */
    private int userId;

    /**
     *  创建任务的时间
     */
    private String createTaskTime;

    /**
     *  预计完成任务的时间
     */
    private String expectedFinishTaskTime;

    /**
     *  完成任务的时间
     */
    private String finishTaskTime;

    /**
     *  任务的优先级
     */
    private int priority;

    /**
     *  任务标题
     */
    private String title;

    /**
     *  任务内容
     */
    private String content;

    public int getToDoId() {
        return toDoId;
    }

    public void setToDoId(int toDoId) {
        this.toDoId = toDoId;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreateTaskTime() {
        return createTaskTime;
    }

    public void setCreateTaskTime(String createTaskTime) {
        this.createTaskTime = createTaskTime;
    }

    public String getExpectedFinishTaskTime() {
        return expectedFinishTaskTime;
    }

    public void setExpectedFinishTaskTime(String expectedFinishTaskTime) {
        this.expectedFinishTaskTime = expectedFinishTaskTime;
    }

    public String getFinishTaskTime() {
        return finishTaskTime;
    }

    public void setFinishTaskTime(String finishTaskTime) {
        this.finishTaskTime = finishTaskTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
