package entidades;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ProdutoService {

    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*teste de commit*/
    
    public void salvarEditarProduto(Produto produto) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (produto.getId() != null && em.find(Cliente.class, produto.getId()) != null) {
                em.merge(produto);
                System.out.println("Cliente atualizado com sucesso.");
            } else {
                em.persist(produto);
                System.out.println("Cliente cadastrado com sucesso.");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void DeletarProduto(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            Produto produto = em.find(Produto.class, id);
            if (produto != null) {
                em.remove(produto);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List<Produto> listarProduto(String sql, Map<String, Object> parametros) {

        EntityManager em = entityManagerFactory.createEntityManager();
        List<Produto> produtos = null;
        try {
            TypedQuery<Produto> query = em.createQuery(sql, Produto.class);
            if (parametros != null) {
                for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            produtos = query.getResultList();
            System.out.println("Clientes encontrados: " + produtos.size());
        } finally {
            em.close();
        }
        return produtos;
    }
}
