package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.utils.JPAUtils;
import org.junit.Test;
import org.junit.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDaoTest {

    private UsuarioDao dao;

    @Test
    public void deveriaAcharUsuarioPeloUsername() {

        EntityManager em = JPAUtils.getEntityManager();

        this.dao = new UsuarioDao(em);

        Usuario usuario = new Usuario("fulano", "fulano@email.com", "123456");

        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();

        Usuario procurado = this.dao.buscarPorUsername(usuario.getNome());

        Assert.assertNotNull(procurado);

    }

    @Test
    public void naoDeveriaAcharUsuarioPeloUsername() {

        EntityManager em = JPAUtils.getEntityManager();

        this.dao = new UsuarioDao(em);

        Usuario usuario = new Usuario("fulano", "fulano@email.com", "123456");

        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();

        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("Beltrano"));

    }

}