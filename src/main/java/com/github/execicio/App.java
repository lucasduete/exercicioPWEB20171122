package com.github.execicio;

import com.github.execicio.Enum.EnumAtivo;
import com.github.execicio.controll.ClienteController;
import com.github.execicio.controll.PedidoController;
import com.github.execicio.model.Cliente;
import com.github.execicio.model.Pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private static Scanner scanner = new Scanner(System.in);
    private static Cliente cliente = null;

    public static void main(String[] args) {

        ArrayList<Cliente> clientes = new ArrayList<>();

        if(clientes == null) {
            cadastroCliente();
        }

        while (true) {
            System.out.printf("Digite 1 para Cadastro ou 2 para Login\n");
            int aux = scanner.nextInt();

            switch (aux) {
                case 1:
                    cliente = cadastroCliente();
                    break;
                case 2:
                    cliente = login();
                    break;
            }

            if (cliente != null)
                break;

            System.out.printf("Credenciais Inalidas, Tente Novamente\n");
        }

        int aux = 0;
        do {
            System.out.println("O que Deseja: ");
            System.out.println("1 - Cadastrar Pedido");
            System.out.println("2 - Listar Pedidos");
            System.out.println("3 - Remover Pedido");
            System.out.println("4 - Atualizar Pedido");
            System.out.println("5 - Sair.");

            aux = scanner.nextInt();

            switch (aux){
                case 1:
                    cadastroPedido();
                    break;
                case 2:
                    listarPedidos();
                    break;
                case 3:
                    removerPedido();
                    break;
                case 4:
                    alterarPedido();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.printf("Opçao Invalida, Tente Novamente\n");
                    break;
            }
        } while (aux != 5);

    }

    private static Cliente login() {

        System.out.println("Digite seu nome para autenticar: ");
        String nome = scanner.next();

        for (Cliente cliente: listCliente())
            if (cliente.getNome().equals(nome))
                return cliente;

        return null;
    }

    private static Cliente cadastroCliente() {

        Cliente cliente = new Cliente();

        System.out.printf("\n\nCadastro:\n\n");

        System.out.println("Digite o nome");
        cliente.setNome(scanner.next());
        System.out.println("Digite seu Documento");
        cliente.setDocumento(scanner.next());
        System.out.println("Digite seu Saldo");
        cliente.setSaldo(scanner.nextDouble());
        cliente.setAtivo(EnumAtivo.ATIVO);

        new ClienteController().incluir(cliente);

        return cliente;
    }

    public static ArrayList<Cliente> listCliente() {

        return new ClienteController().listar();
    }

    private static Pedido cadastroPedido() {

        Pedido pedido = new Pedido();

        System.out.println("Digite o Valor do Pedido");
        pedido.setValor(scanner.nextDouble());
        pedido.setCliente(cliente);
        pedido.setData(LocalDate.now());

        new PedidoController().incluir(pedido);

        return pedido;
    }

    private static void listarPedidos() {

        System.out.println(new PedidoController().listar(cliente.getId()));
    }

    private static void removerPedido() {

        Pedido pedido = new Pedido();

        System.out.println("Digite o Id do Pedido");
        int aux = scanner.nextInt();

        pedido.setId(aux);

        new PedidoController().excluir(pedido);
    }

    private static void alterarPedido() {

        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setData(LocalDate.now());

        System.out.println("Digite o Id do Pedido");
        int aux = scanner.nextInt();
        System.out.println("Digite o Valor do Pedido");
        double valor = scanner.nextDouble();

        pedido.setId(aux);
        pedido.setValor(valor);

        new PedidoController().alterar(pedido);
    }
}
