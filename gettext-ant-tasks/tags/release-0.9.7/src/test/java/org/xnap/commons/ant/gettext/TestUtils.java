package org.xnap.commons.ant.gettext;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

public class TestUtils {
	
	/**
	 * Sets a target and project on <code>task</code>.
	 */
	public static void setTargetAndProject(Task task) {
		Target target = new Target();
		Project project = new Project();
		project.init();
		target.setProject(project);
		task.setOwningTarget(target);
		task.setProject(project);
	}
}
