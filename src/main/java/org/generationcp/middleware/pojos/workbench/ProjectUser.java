/*******************************************************************************
 * Copyright (c) 2012, All Rights Reserved.
 * 
 * Generation Challenge Programme (GCP)
 * 
 * 
 * This software is licensed for use under the terms of the GNU General Public
 * License (http://bit.ly/8Ztv8M) and the provisions of Part F of the Generation
 * Challenge Programme Amended Consortium Agreement (http://bit.ly/KQX1nL)
 * 
 *******************************************************************************/

package org.generationcp.middleware.pojos.workbench;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.generationcp.middleware.pojos.User;

/**
 * The Class ProjectUser.
 * 
 *  @author Joyce Avestro
 *  
 */
@Entity
@Table(name = "workbench_project_user")
public class ProjectUser implements Serializable{

    private static final long serialVersionUID = 1L;

    
    
    public static final String GET_USERS_BY_PROJECT_ID = 
            "SELECT users.userid, users.instalid, users.ustatus, users.uaccess, users.utype, " +
            "users.uname, users.upswd, users.personid, users.adate, users.cdate " + 
            "FROM users JOIN workbench_project_user pu ON users.userid = pu.user_id " +
            "WHERE pu.project_id = :projectId";
    
    public static final String COUNT_METHODS_BY_PROJECT_ID = 
            "SELECT COUNT(users.userid) " + 
            "FROM users JOIN workbench_project_user pu ON users.userid = pu.user_id " +
            "WHERE pu.project_id = :projectId";
    
    @Id
    @Basic(optional = false)
    @GeneratedValue
    @Column(name = "project_user_id")
    private Long projectUserId;

    /** The project. */
    @OneToOne(optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    /** The user. */
    @Column(name = "user_id")
    private Integer userId;

    
    public ProjectUser() {
    }

    public ProjectUser(Long projectUserId, Project project, Integer userId) {
        this.projectUserId = projectUserId;
        this.project = project;
        this.userId = userId;
    }

    public ProjectUser(Project project, User user) {
        this.project = project;
        this.userId = user.getUserid();
    }

    public Long getProjectUserId() {
        return projectUserId;
    }
    
    public void setProjectUserId(Long projectUserId) {
        this.projectUserId = projectUserId;
    }
    
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(projectUserId).hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!ProjectUser.class.isInstance(obj)) {
            return false;
        }

        ProjectUser otherObj = (ProjectUser) obj;

        return new EqualsBuilder().append(projectUserId, otherObj.projectUserId).isEquals();
    }
    
    @Override
    public String toString() {
        return "ProjectUser [projectUserId=" + projectUserId +
                ", project=" + project +
                ", userId=" + userId + "]";
    }

}