package entidades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class ClienteService {

    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SalvarEdiarCliente(Cliente cliente) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (cliente.getId() != null && em.find(Cliente.class, cliente.getId()) != null) {
                em.merge(cliente);
                System.out.println("Cliente atualizado com sucesso.");
            } else {
                em.persist(cliente);
                System.out.println("Cliente cadastrado com sucesso.");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.clear();
        }
    }

    public void DeletarCliente(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, id);
            if (cliente != null) {
                em.remove(cliente);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List<Cliente> listarCliente(String sql, Map<String, Object> parametros) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Cliente> clientes = null;
        try {
            TypedQuery<Cliente> query = em.createQuery(sql, Cliente.class);
            if (parametros != null && !parametros.isEmpty()) {
                for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            } else {
                
            }
            clientes = query.getResultList();
            System.out.println("Clientes encontrados: " + clientes.size());
        } finally {
            em.close();
        }
        return clientes;
    }

    public Cliente buscarClientePorId(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Cliente cliente = null;
        try {
            cliente = em.find(Cliente.class, id);
        } finally {
            em.close();
        }
        return cliente;
    }

}
