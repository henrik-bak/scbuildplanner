/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ezzored.starcraftbuildplanner.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author Ezzored
 */
@DatabaseTable(tableName = "builds")
public class Build {
    
    public static final String NAME_FIELD_NAME = "name";
    public static final String MATCHUP_FIELD_NAME = "matchup";
    public static final String SCRIPT_FIELD_NAME = "script";
    public static final String NOTES_FIELD_NAME = "notes";
    public static final String CATEGORY_FIELD_NAME = "category";
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
    private String name;
    @DatabaseField(columnName = MATCHUP_FIELD_NAME, canBeNull = false)
    private String matchup;
    @DatabaseField(columnName = SCRIPT_FIELD_NAME, canBeNull = false)
    private String script;
    @DatabaseField(columnName = NOTES_FIELD_NAME, canBeNull = true)
    private String notes;
    @DatabaseField(columnName = CATEGORY_FIELD_NAME, canBeNull = true)
    private String category;
    
    Build(){}

    public Build(String name, String matchup, String script, String notes, String category) {
        this.name = name;
        this.matchup = matchup;
        this.script = script;
        this.notes = notes;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatchup() {
        return matchup;
    }

    public void setMatchup(String matchup) {
        this.matchup = matchup;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + id;
		result = prime * result + ((matchup == null) ? 0 : matchup.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((script == null) ? 0 : script.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Build other = (Build) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (id != other.id)
			return false;
		if (matchup == null) {
			if (other.matchup != null)
				return false;
		} else if (!matchup.equals(other.matchup))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (script == null) {
			if (other.script != null)
				return false;
		} else if (!script.equals(other.script))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Build [id=" + id + ", name=" + name + ", matchup=" + matchup
				+ ", script=" + script + ", notes=" + notes + ", category="
				+ category + "]";
	}

}
