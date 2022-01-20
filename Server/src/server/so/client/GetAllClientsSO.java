/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.client;

import commonlib.domain.Client;
import java.util.List;
import server.repository.db.DbRepository;
import server.repository.db.impl.RepositoryClient;
import server.so.AbstractSO;

/**
 *
 * @author ANA
 */
public class GetAllClientsSO extends AbstractSO{
    private final DbRepository repositoryClient;

    public GetAllClientsSO() {
        this.repositoryClient = new RepositoryClient();
    }

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        List<Client> clients = repositoryClient.getAll();
        for (Client client : clients) {
            ((List<Client>)param).add(client);
        }
    }

    @Override
    protected void commitTransaction() throws Exception {
        repositoryClient.commit();
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        repositoryClient.rollback();
    }
}
