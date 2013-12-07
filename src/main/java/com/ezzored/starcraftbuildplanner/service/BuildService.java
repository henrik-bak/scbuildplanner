/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ezzored.starcraftbuildplanner.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ezzored.starcraftbuildplanner.model.Build;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ezzored
 */
public class BuildService {

    Dao<Build, Integer> buildDao;

    public BuildService() throws SQLException {

        File path = new File("D:/db/");

        String databaseUrl = "jdbc:h2:file:" + path.getAbsolutePath() + "sc.h2.db";
        // create a connection source to our database
        ConnectionSource connectionSource
                = new JdbcConnectionSource(databaseUrl);

        // instantiate the dao
        buildDao
                = DaoManager.createDao(connectionSource, Build.class);

        // if you need to create the 'accounts' table make this call
        TableUtils.createTableIfNotExists(connectionSource, Build.class);
    }

    public void save(Build build) {
        try {
            buildDao.create(build);
        } catch (SQLException ex) {
            Logger.getLogger(BuildService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Build selectedBuild, Build updatedBuild) {
        List<Build> matching = null;
        try {
            matching = buildDao.queryForMatching(selectedBuild);

            if (matching != null) {
                Build match = matching.get(0);

                if (match != null) {
                    match.setMatchup(updatedBuild.getMatchup());
                    match.setName(updatedBuild.getName());
                    match.setNotes(updatedBuild.getNotes());
                    match.setScript(updatedBuild.getScript());

                    buildDao.createOrUpdate(match);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BuildService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Build> getAll() {
        List<Build> returnList;
        try {
            returnList = buildDao.queryForAll();
        } catch (SQLException ex) {
            returnList = new ArrayList<>();
        }
        return returnList;
    }

    public void delete(Build build) {
        try {
            buildDao.delete(build);
        } catch (SQLException ex) {
            Logger.getLogger(BuildService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
