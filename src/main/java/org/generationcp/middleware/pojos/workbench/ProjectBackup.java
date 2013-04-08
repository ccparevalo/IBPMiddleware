package org.generationcp.middleware.pojos.workbench;

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

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "workbench_project_backup")
public class ProjectBackup implements Serializable{
    private static final long serialVersionUID = 1L;

    public static final String GET_ALL_DISTINCT_PROJECT_BACKUP =
        "select project_backup_id, project_id, backup_time, backup_path from workbench_project_backup group by project_id";

    public ProjectBackup(Integer projectBackupId, Integer projectId, Date backupTime, String backupPath) {
        this.projectBackupId = projectBackupId;
        this.projectId = projectId;
        this.backupTime = backupTime;
        this.backupPath = backupPath;
    }

    public ProjectBackup() {
    }

    @Id
    @Basic(optional = false)
    @GeneratedValue
    @Column(name = "project_backup_id")
    private Integer projectBackupId;

    @Basic(optional = false)
    @Column(name = "project_id")
    private Integer projectId;

    @Basic(optional = false)
    @Column(name = "backup_time")
    private Date backupTime;

    @Basic(optional = false)
    @Column(name = "backup_path")
    private String backupPath;

    public Integer getProjectBackupId() {
        return projectBackupId;
    }

    public void setProjectBackupId(Integer projectBackupId) {
        this.projectBackupId = projectBackupId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getBackupTime() {
        return backupTime;
    }

    public void setBackupTime(Date backupTime) {
        this.backupTime = backupTime;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(projectBackupId).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjectBackup other = (ProjectBackup) obj;
        if (projectBackupId == null) {
            if (other.projectBackupId != null)
                return false;
        } else if (!projectBackupId.equals(other.projectBackupId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Workbench Project Backup [projectBackupId=");
        builder.append(projectBackupId);
        builder.append(", projectId=");
        builder.append(projectId);
        builder.append(", backupPath=");
        builder.append(backupPath);
        builder.append(", backupTime=");
        builder.append(backupTime);

        builder.append("]");
        return builder.toString();
    }
}