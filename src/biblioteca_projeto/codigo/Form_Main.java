
package biblioteca_projeto.codigo;
import java.io.*;
import java.io.File;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import library.Book;
import library.Publisher;
import user.Employee;
import user.Grad_student;
import user.Person;
import user.Postgrad_student;

public class Form_Main extends javax.swing.JFrame {
    ArrayList<Person> personList = new ArrayList<Person>();//lista de pessoas
    ArrayList<Publisher> publisherList = new ArrayList<>();//lista de editoras
    ArrayList<Book> bookList = new ArrayList<>();//lista de livros
    String emailHolder;//variavel axiliar que segura o email da pessoa que estiver logada
    int modo;//variavel auxiliar que determina qual modo da tabela vai ser chamada
    int modoFile;//variavel auxiliar que determina qual modo de File vai ser usado
    
    public void LoadCBEmployee(){//combo-box da tela dos funcionarios para escolher a editora
        CB_Employee_Publishers.removeAllItems();
        CB_Employee_Publishers.addItem("Selecione");
        for(int i=0;i<publisherList.size();i++){
            CB_Employee_Publishers.addItem(publisherList.get(i).getPublisherName());//poe as editoras no combo-box
        }
            
    }    
    public void LoadTablePublisher(){//funcao para dar load na tabela de editoras da tela de funcionarios
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome","Endereço"},0);
            for(int i=0;i<publisherList.size();i++){
                    Object linha[] = new Object[]{publisherList.get(i).getPublisherName(),
                                                  publisherList.get(i).getAddress()};
            modelo.addRow(linha);
        }
            Table_Employee_Publisher.setModel(modelo);
            LoadCBEmployee();
    }
    public void LoadTableBooks(){//funcao para dar load na tabela de livros
        if(modo == 0){// da load na tabela de livros que esta na tela do funcionarios
            DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Tıtulo","Autor","Editora","Disponível"},0);
            for(int i=0;i<bookList.size();i++){
                Object linha[] = new Object[]{bookList.get(i).getTitle(),
                                              bookList.get(i).getAuthor(),
                                              bookList.get(i).getPublisher().getPublisherName(),
                                              bookList.get(i).getAvailable()};
                modelo.addRow(linha);
            }
            Table_Employee_Books.setModel(modelo);    
        }
        else if(modo == 1){//da load na tabela de liivros da tela dos usuarios
            DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Tıtulo","Autor","Editora","Disponível"},0);
            for(int i=0;i<bookList.size();i++){
                Object linha[] = new Object[]{bookList.get(i).getTitle(),
                                              bookList.get(i).getAuthor(),
                                              bookList.get(i).getPublisher().getPublisherName(),
                                              bookList.get(i).getAvailable()};
            modelo.addRow(linha);
        }
            Table_UserInterface_Search.setModel(modelo); 
        }
        else if(modo == 2){//da load na tabela de liivros da tela dos usuarios quando o usuario limita os livros pela busca
            DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Tıtulo","Autor","Editora","Disponível"},0);
            String book = Txt_UserInterface_Procurar.getText();
            for(int i=0;i<bookList.size();i++){
                if(book.equals(bookList.get(i).getTitle())){
                    Object linha[] = new Object[]{bookList.get(i).getTitle(),
                                                  bookList.get(i).getAuthor(),
                                                  bookList.get(i).getPublisher().getPublisherName(),
                                                  bookList.get(i).getAvailable()};
                    modelo.addRow(linha);
                }    
            }
            Table_UserInterface_Search.setModel(modelo);
        }
    }
    public void LoadTableMyBooks(){//da load na tabela de livros pessoais de cada grad_student ou postgrad_student que estiver logado
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Tıtulo","Autor","Editora"},0);
        Person person = new Person();
        for(int j = 0; j<personList.size(); j++){
            if(personList.get(j).getEmail().equals(emailHolder)){
                for(int i=0;i<personList.get(j).getBookList().size();i++){
                    Object linha[] = new Object[]{personList.get(j).getBookList().get(i).getTitle(),
                                                  personList.get(j).getBookList().get(i).getAuthor(),
                                                  personList.get(j).getBookList().get(i).getPublisher().getPublisherName()};
                    modelo.addRow(linha);
                    }               
                }
            }
            Table_UserInterface_MyBooks.setModel(modelo);  
    }

    public void DataRecorder(int modoFile, ArrayList<?> genericList){//metodo para gravar dados das pessoas e editoras e livros
        try{
            File file = new File("");
            String line = "";
            if(modoFile == 0){
                ArrayList<Person> person = new ArrayList<>();
                person = (ArrayList<Person>) genericList;
                file = new File("PeopleData.txt");              
                if(file.exists()){
                    //System.out.println("Arquivo ja existente");
                }
                else{
                    file.createNewFile();
                }            
                FileWriter writer = new FileWriter(file);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                for(int i = 0; i<genericList.size(); i++){
                    if(person.get(i) instanceof Grad_student){//se for grad_student
                        line = "Grad_student;";
                        Grad_student grad = new Grad_student();
                        grad = (Grad_student) person.get(i);
                        line+=grad.getPersonName()+";"+grad.getEmail()+";"+grad.getPassword()+";"+grad.getRa()+";";//grava infos gerais
                        for(int j = 0; j<grad.getBookList().size(); j++){//grava a lista de livros do usario
                            line+=grad.getBookList().get(j).getTitle()+";";
                            line+=grad.getBookList().get(j).getAuthor()+";";
                            line+=grad.getBookList().get(j).getPublisher().getPublisherName()+";";
                            line+=grad.getBookList().get(j).getAvailable()+";";
                        }
                        line+="\n";
                        bufferWriter.write(line);
                    }
                    else if(person.get(i) instanceof Postgrad_student){//se for Postgrad_student
                        line = "Postgrad_student;";
                        Postgrad_student pgrad = new Postgrad_student();
                        pgrad = (Postgrad_student) person.get(i);
                        line+=pgrad.getPersonName()+";"+pgrad.getEmail()+";"+pgrad.getPassword()+";"+pgrad.getCoo()+";";//grava infos gerais
                        for(int j = 0; j<pgrad.getBookList().size(); j++){//grava a lista de livros do usario
                            line+=pgrad.getBookList().get(j).getTitle()+";";
                            line+=pgrad.getBookList().get(j).getAuthor()+";";
                            line+=pgrad.getBookList().get(j).getPublisher().getPublisherName()+";";
                            line+=pgrad.getBookList().get(j).getAvailable()+";";
                        }
                        line+="\n";
                        bufferWriter.write(line);
                    }
                    else if(person.get(i) instanceof Employee){//se for funcionario
                        line = "Employee;";
                        Employee emp = new Employee();
                        emp = (Employee) person.get(i);
                        line+=emp.getPersonName()+";"+emp.getEmail()+";"+emp.getPassword()+";\n";
                        bufferWriter.write(line);
                    }
                }
                writer.flush();
                bufferWriter.flush();
                bufferWriter.close();
                writer.close();
            }
            else if(modoFile == 1){//para gravar os dados das editoras
                ArrayList<Publisher> publisher = new ArrayList<>();
                publisher = (ArrayList<Publisher>) genericList;
                file = new File ("PublisherData.txt");
                if (!file.exists()){
                    file.createNewFile();
                }
                FileWriter writer = new FileWriter(file);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                for(int i = 0; i<genericList.size(); i++){
                    Publisher pub = new Publisher();
                    pub = (Publisher) publisher.get(i);
                    line=pub.getPublisherName()+";"+pub.getAddress()+";";//grava infos gerais
                        for(int j = 0; j<pub.getBookList().size(); j++){//grava a lista de livros da editora
                            line+=pub.getBookList().get(j).getTitle()+";"+pub.getBookList().get(j).getAuthor()+";";
                        }
                    line+="\n";
                    bufferWriter.write(line);
                    }
                writer.flush();
                bufferWriter.flush();
                bufferWriter.close();
                writer.close();
            }            
            else if(modoFile == 2){//grava informacoes dos livros
                ArrayList<Book> book = new ArrayList<>();
                book = (ArrayList<Book>) genericList;
                file = new File ("BookData.txt");
                if (!file.exists()){
                    file.createNewFile();
                }
                FileWriter writer = new FileWriter(file);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                for(int i = 0; i<genericList.size(); i++){
                    Book b = new Book();
                    b = (Book) book.get(i);
                    line=b.getTitle()+";"+b.getAuthor()+";"+b.getPublisher().getPublisherName()+";"+b.getAvailable()+";\n";
                    bufferWriter.write(line);
                }
                writer.flush();
                bufferWriter.flush();
                bufferWriter.close();
                writer.close();
            }
        }catch(IOException e){
            System.out.println("Erro: "+e.toString());
         }
    }
    
    
    
    public Form_Main() throws IOException {
        initComponents();
        File filePoepleData = new File("PeopleData.txt");//file para ler o txt das pessoas
        File filePublisherData = new File("PublisherData.txt");//file para ler o txt das editoras
        File fileBookData = new File("BookData.txt");//file para ler o txt dos livros
        String line = "";
        
        try{//leitura do arquivo dos livros
            if(fileBookData.exists()){
                FileReader reader = new FileReader(fileBookData);
                BufferedReader bufferReader = new BufferedReader(reader);
                while(bufferReader.ready()){
                    line = bufferReader.readLine();
                    String[] data = line.split(";");
                    Book book = new Book();
                    book.setTitle(data[0]);
                    book.setAuthor(data[1]);
                    book.getPublisher().setPublisherName(data[2]);
                    book.setAvailable(data[3]);
                    bookList.add(book);
                }
                bufferReader.close();
                reader.close(); 
            }            
        }catch (FileNotFoundException ex) {
                Logger.getLogger(Form_Main.class.getName()).log(Level.SEVERE, null, ex);
        }        
        try{//leitura das editoras
            if(filePublisherData.exists()){
                FileReader reader = new FileReader(filePublisherData);
                BufferedReader bufferReader = new BufferedReader(reader);
                while(bufferReader.ready()){
                    line = bufferReader.readLine();
                    String[] data = line.split(";");
                    Publisher pub = new Publisher();
                    pub.setPublisherName(data[0]);
                    pub.setAddress(data[1]);
                    for(int i = 0; i<(data.length-2)/2;i++){
                        Book book = new Book();
                        pub.getBookList().add(book);
                        pub.getBookList().get(i).setTitle(data[2+(2*i)]);
                        pub.getBookList().get(i).setAuthor(data[3+(2*i)]);  
                    }
                    publisherList.add(pub);
                }
                bufferReader.close();
                reader.close();
            }
        }catch (FileNotFoundException ex) {
                Logger.getLogger(Form_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {//leitura do arquivo das pessoas
            if(filePoepleData.exists()){
                FileReader reader = new FileReader(filePoepleData);
                BufferedReader bufferReader = new BufferedReader(reader);
                while(bufferReader.ready()){
                    line = bufferReader.readLine();
                    String[] data = line.split(";");
                    if(data[0].equals("Employee")){//leitura dos funcionarios
                        Employee emp = new Employee();
                        emp.setPersonName(data[1]);
                        emp.setEmail(data[2]);
                        emp.setPassword(data[3]);
                        personList.add(emp);
                    }
                    else if(data[0].equals("Grad_student")){//leitura dos alunos de graduacao
                        Grad_student grad = new Grad_student();
                        grad.setPersonName(data[1]);
                        grad.setEmail(data[2]);
                        grad.setPassword(data[3]);
                        grad.setRa(data[4]);
                        for(int i = 0; i<(data.length-5)/4;i++){//leitura dos livros reservados pelo aluno
                                Book book = new Book();
                                grad.getBookList().add(book);
                                grad.getBookList().get(i).setTitle(data[5+(4*i)]);
                                grad.getBookList().get(i).setAuthor(data[6+(4*i)]);
                                grad.getBookList().get(i).getPublisher().setPublisherName(data[7+(4*i)]);
                                grad.getBookList().get(i).setAvailable(data[8+(4*i)]);  
                        }
                        personList.add(grad);
                    }                        
                    else if(data[0].equals("Postgrad_student")){//leitura dos alunos de pos
                        Postgrad_student pos = new Postgrad_student();
                        pos.setPersonName(data[1]);
                        pos.setEmail(data[2]);
                        pos.setPassword(data[3]);
                        pos.setCoo(data[4]);
                        for(int i = 0; i<(data.length-5)/4;i++){//leitura dos livros reservados pelo aluno
                                Book book = new Book();
                                pos.getBookList().add(book);
                                pos.getBookList().get(i).setTitle(data[5+(4*i)]);
                                pos.getBookList().get(i).setAuthor(data[6+(4*i)]);
                                pos.getBookList().get(i).getPublisher().setPublisherName(data[7+(4*i)]);
                                pos.getBookList().get(i).setAvailable(data[8+(4*i)]);  
                        }
                        personList.add(pos);
                    }
                }    
                bufferReader.close();
                reader.close();
            }    
        }catch (FileNotFoundException ex) {
            Logger.getLogger(Form_Main.class.getName()).log(Level.SEVERE, null, ex);
        }                  
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel_Main = new javax.swing.JPanel();
        Panel_Welcome = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Btn_Welcome_Entrar = new javax.swing.JButton();
        Btn_Welcome_Cadastrar = new javax.swing.JButton();
        Panel_Login = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Btn_Login_Voltar = new javax.swing.JButton();
        Btn_Login_Confirmar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Txt_Login_Email = new javax.swing.JTextField();
        Txt_Login_Password = new javax.swing.JPasswordField();
        Panel_Register = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Btn_Register_Confirmar = new javax.swing.JButton();
        Btn_Register_Voltar = new javax.swing.JButton();
        CB_Register_Tipo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Txt_Register_Nome = new javax.swing.JTextField();
        Txt_Register_Email = new javax.swing.JTextField();
        Txt_Register_RAeCOO = new javax.swing.JTextField();
        Txt_Register_Password = new javax.swing.JPasswordField();
        Panel_UserInterface = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        Btn_UserInterface_Voltar = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel15 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        Txt_UserInterface_Procurar = new javax.swing.JTextField();
        Btn_UserInterface_Procurar = new javax.swing.JButton();
        Btn_UserInterface_CancelarPesquisa = new javax.swing.JButton();
        Btn_UserInterface_Reservar = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        Table_UserInterface_Search = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        Table_UserInterface_MyBooks = new javax.swing.JTable();
        Btn_UserInterface_Devolver = new javax.swing.JButton();
        Panel_Employee = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        Btn_Employee_Voltar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Employee_Publisher = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        Txt_Employee_NamePublisher = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        Txt_Employee_AddressPublisher = new javax.swing.JTextField();
        Btn_Employee_Publisher_Salvar = new javax.swing.JButton();
        Btn_Employee_Publisher_Cancelar = new javax.swing.JButton();
        Btn_Employee_Publisher_Novo = new javax.swing.JButton();
        Btn_Employee_Publisher_Excluir = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Employee_Books = new javax.swing.JTable();
        Btn_Employee_Books_Excluir = new javax.swing.JButton();
        Btn_Employee_Books_Novo = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        Txt_Employee_BookTitle = new javax.swing.JTextField();
        Txt_Employee_BookAuthor = new javax.swing.JTextField();
        Btn_Employee_Books_Salvar = new javax.swing.JButton();
        Btn_Employee_Books_Cancelar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        CB_Employee_Publishers = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(552, 637));

        Panel_Main.setLayout(new java.awt.CardLayout());

        Panel_Welcome.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png"))); // NOI18N

        Btn_Welcome_Entrar.setText("Entrar");
        Btn_Welcome_Entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Welcome_EntrarActionPerformed(evt);
            }
        });

        Btn_Welcome_Cadastrar.setText("Cadastrar");
        Btn_Welcome_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Welcome_CadastrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_WelcomeLayout = new javax.swing.GroupLayout(Panel_Welcome);
        Panel_Welcome.setLayout(Panel_WelcomeLayout);
        Panel_WelcomeLayout.setHorizontalGroup(
            Panel_WelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Panel_WelcomeLayout.createSequentialGroup()
                .addGroup(Panel_WelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_WelcomeLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel1))
                    .addGroup(Panel_WelcomeLayout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addGroup(Panel_WelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Btn_Welcome_Entrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Btn_Welcome_Cadastrar, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        Panel_WelcomeLayout.setVerticalGroup(
            Panel_WelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_WelcomeLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addComponent(Btn_Welcome_Entrar)
                .addGap(27, 27, 27)
                .addComponent(Btn_Welcome_Cadastrar)
                .addGap(30, 30, 30))
        );

        Panel_Main.add(Panel_Welcome, "Welcome");

        Panel_Login.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png"))); // NOI18N

        Btn_Login_Voltar.setText("Voltar");
        Btn_Login_Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Login_VoltarActionPerformed(evt);
            }
        });

        Btn_Login_Confirmar.setText("Confirmar");
        Btn_Login_Confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Login_ConfirmarActionPerformed(evt);
            }
        });

        jLabel3.setText("E-mail:");

        jLabel4.setText("Password:");

        javax.swing.GroupLayout Panel_LoginLayout = new javax.swing.GroupLayout(Panel_Login);
        Panel_Login.setLayout(Panel_LoginLayout);
        Panel_LoginLayout.setHorizontalGroup(
            Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Panel_LoginLayout.createSequentialGroup()
                .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_LoginLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel2))
                    .addGroup(Panel_LoginLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Txt_Login_Email)
                            .addComponent(Txt_Login_Password, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))))
                .addContainerGap(93, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_LoginLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Btn_Login_Voltar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_Login_Confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(229, 229, 229))
        );
        Panel_LoginLayout.setVerticalGroup(
            Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_LoginLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Txt_Login_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(Panel_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Txt_Login_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(Btn_Login_Confirmar)
                .addGap(18, 18, 18)
                .addComponent(Btn_Login_Voltar)
                .addGap(33, 33, 33))
        );

        Panel_Main.add(Panel_Login, "Login");

        Panel_Register.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        Btn_Register_Confirmar.setText("Confirmar");
        Btn_Register_Confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Register_ConfirmarActionPerformed(evt);
            }
        });

        Btn_Register_Voltar.setText("Voltar");
        Btn_Register_Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Register_VoltarActionPerformed(evt);
            }
        });

        CB_Register_Tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Funcionário", "Graduação", "Pós-Graduação" }));
        CB_Register_Tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CB_Register_TipoActionPerformed(evt);
            }
        });

        jLabel5.setText("Nome:");

        jLabel6.setText("Email:");

        jLabel7.setText("Password:");

        jLabel8.setText("RA/COO:");

        Txt_Register_RAeCOO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Txt_Register_RAeCOOActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_RegisterLayout = new javax.swing.GroupLayout(Panel_Register);
        Panel_Register.setLayout(Panel_RegisterLayout);
        Panel_RegisterLayout.setHorizontalGroup(
            Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Panel_RegisterLayout.createSequentialGroup()
                .addGroup(Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_RegisterLayout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addGroup(Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Btn_Register_Confirmar, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                .addComponent(Btn_Register_Voltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(CB_Register_Tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(Panel_RegisterLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(43, 43, 43)
                        .addGroup(Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Txt_Register_Email)
                            .addComponent(Txt_Register_RAeCOO, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                            .addComponent(Txt_Register_Password)
                            .addComponent(Txt_Register_Nome))))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        Panel_RegisterLayout.setVerticalGroup(
            Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_RegisterLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CB_Register_Tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addGroup(Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Txt_Register_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Txt_Register_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Txt_Register_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(Panel_RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(Txt_Register_RAeCOO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(Btn_Register_Confirmar)
                .addGap(18, 18, 18)
                .addComponent(Btn_Register_Voltar)
                .addGap(47, 47, 47))
        );

        Panel_Main.add(Panel_Register, "Register");

        Panel_UserInterface.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(51, 153, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 115, Short.MAX_VALUE)
        );

        Btn_UserInterface_Voltar.setText("Voltar");
        Btn_UserInterface_Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_UserInterface_VoltarActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel19.setText("Tıtulo:");

        Btn_UserInterface_Procurar.setText("Procurar");
        Btn_UserInterface_Procurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_UserInterface_ProcurarActionPerformed(evt);
            }
        });

        Btn_UserInterface_CancelarPesquisa.setText("Cancelar");
        Btn_UserInterface_CancelarPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_UserInterface_CancelarPesquisaActionPerformed(evt);
            }
        });

        Btn_UserInterface_Reservar.setText("Reservar");
        Btn_UserInterface_Reservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_UserInterface_ReservarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19)
                        .addGap(64, 64, 64)
                        .addComponent(Txt_UserInterface_Procurar, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(Btn_UserInterface_Procurar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_UserInterface_Reservar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_UserInterface_CancelarPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(Txt_UserInterface_Procurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_UserInterface_Procurar)
                    .addComponent(Btn_UserInterface_CancelarPesquisa)
                    .addComponent(Btn_UserInterface_Reservar)))
        );

        Table_UserInterface_Search.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tıtulo", "Autor", "Editora", "Disponível"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_UserInterface_Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_UserInterface_SearchMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(Table_UserInterface_Search);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jTabbedPane4.addTab("Estante virtual", jPanel15);

        Table_UserInterface_MyBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tıtulo", "Autor", "Editora"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_UserInterface_MyBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_UserInterface_MyBooksMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(Table_UserInterface_MyBooks);

        Btn_UserInterface_Devolver.setText("Devolver");
        Btn_UserInterface_Devolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_UserInterface_DevolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(216, 216, 216)
                .addComponent(Btn_UserInterface_Devolver, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(Btn_UserInterface_Devolver)
                .addGap(28, 28, 28))
        );

        jTabbedPane4.addTab("Meus livros", jPanel16);

        javax.swing.GroupLayout Panel_UserInterfaceLayout = new javax.swing.GroupLayout(Panel_UserInterface);
        Panel_UserInterface.setLayout(Panel_UserInterfaceLayout);
        Panel_UserInterfaceLayout.setHorizontalGroup(
            Panel_UserInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_UserInterfaceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4)
                .addContainerGap())
            .addGroup(Panel_UserInterfaceLayout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(Btn_UserInterface_Voltar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_UserInterfaceLayout.setVerticalGroup(
            Panel_UserInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_UserInterfaceLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(Btn_UserInterface_Voltar)
                .addGap(25, 25, 25))
        );

        Panel_Main.add(Panel_UserInterface, "UserInterface");

        Panel_Employee.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Employee.setMaximumSize(new java.awt.Dimension(560, 637));

        jPanel5.setBackground(new java.awt.Color(51, 153, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        Btn_Employee_Voltar.setText("Voltar");
        Btn_Employee_Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Employee_VoltarActionPerformed(evt);
            }
        });

        Table_Employee_Publisher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Endereço"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Employee_Publisher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_Employee_PublisherMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Employee_Publisher);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel10.setText("Nome:");

        jLabel11.setText("Endereço:");

        Btn_Employee_Publisher_Salvar.setText("Salvar");
        Btn_Employee_Publisher_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Employee_Publisher_SalvarActionPerformed(evt);
            }
        });

        Btn_Employee_Publisher_Cancelar.setText("Cancelar");
        Btn_Employee_Publisher_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Employee_Publisher_CancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(44, 44, 44)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(Btn_Employee_Publisher_Salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(Btn_Employee_Publisher_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Txt_Employee_NamePublisher)
                        .addComponent(Txt_Employee_AddressPublisher, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(Txt_Employee_NamePublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(Txt_Employee_AddressPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Employee_Publisher_Salvar)
                    .addComponent(Btn_Employee_Publisher_Cancelar)))
        );

        Btn_Employee_Publisher_Novo.setText("Novo");
        Btn_Employee_Publisher_Novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Employee_Publisher_NovoActionPerformed(evt);
            }
        });

        Btn_Employee_Publisher_Excluir.setText("Excluir");
        Btn_Employee_Publisher_Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Employee_Publisher_ExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(Btn_Employee_Publisher_Novo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(Btn_Employee_Publisher_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Employee_Publisher_Novo)
                    .addComponent(Btn_Employee_Publisher_Excluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jTabbedPane1.addTab("Editora", jPanel12);

        Table_Employee_Books.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tıtulo", "Autor", "Editora", "Disponível"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Employee_Books.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_Employee_BooksMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table_Employee_Books);

        Btn_Employee_Books_Excluir.setText("Excluir");
        Btn_Employee_Books_Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Employee_Books_ExcluirActionPerformed(evt);
            }
        });

        Btn_Employee_Books_Novo.setText("Novo");
        Btn_Employee_Books_Novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Employee_Books_NovoActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel12.setText("Tıtulo:     ");

        Btn_Employee_Books_Salvar.setText("Salvar");
        Btn_Employee_Books_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Employee_Books_SalvarActionPerformed(evt);
            }
        });

        Btn_Employee_Books_Cancelar.setText("Cancelar");
        Btn_Employee_Books_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Employee_Books_CancelarActionPerformed(evt);
            }
        });

        jLabel14.setText("Autor:");

        jLabel13.setText("Editora:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13))
                .addGap(44, 44, 44)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Txt_Employee_BookTitle)
                            .addComponent(Txt_Employee_BookAuthor, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(CB_Employee_Publishers, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_Employee_Books_Salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)))
                .addComponent(Btn_Employee_Books_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(Txt_Employee_BookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(Txt_Employee_BookAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Employee_Books_Salvar)
                    .addComponent(Btn_Employee_Books_Cancelar)
                    .addComponent(jLabel13)
                    .addComponent(CB_Employee_Publishers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(Btn_Employee_Books_Novo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(Btn_Employee_Books_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Employee_Books_Novo)
                    .addComponent(Btn_Employee_Books_Excluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jTabbedPane1.addTab("Livros", jPanel11);

        javax.swing.GroupLayout Panel_EmployeeLayout = new javax.swing.GroupLayout(Panel_Employee);
        Panel_Employee.setLayout(Panel_EmployeeLayout);
        Panel_EmployeeLayout.setHorizontalGroup(
            Panel_EmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Panel_EmployeeLayout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(Btn_Employee_Voltar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Panel_EmployeeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        Panel_EmployeeLayout.setVerticalGroup(
            Panel_EmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_EmployeeLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(Btn_Employee_Voltar)
                .addGap(22, 22, 22))
        );

        Panel_Main.add(Panel_Employee, "Employee");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_Welcome_EntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Welcome_EntrarActionPerformed
        //botao da tela inicial para entrar na tela de login
        CardLayout cl = (CardLayout) Panel_Main.getLayout();
        cl.show(Panel_Main, "Login");
    }//GEN-LAST:event_Btn_Welcome_EntrarActionPerformed

    private void Btn_Login_VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Login_VoltarActionPerformed
        //botao da tela de login para voltar para a tela inicial
        CardLayout cl = (CardLayout) Panel_Main.getLayout();
        cl.show(Panel_Main, "Welcome");
    }//GEN-LAST:event_Btn_Login_VoltarActionPerformed

    private void Btn_Welcome_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Welcome_CadastrarActionPerformed
        //botao da tela inicial que chama a tela de cadastro
        //os campos de RA e COO vem desabilitados pq a primeira opcao eh para funcionarios
        CardLayout cl = (CardLayout) Panel_Main.getLayout();
        cl.show(Panel_Main, "Register");
        jLabel8.setEnabled(false);
        Txt_Register_RAeCOO.setEnabled(false);
    }//GEN-LAST:event_Btn_Welcome_CadastrarActionPerformed

    private void Btn_Register_VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Register_VoltarActionPerformed
        //botao da tela de cadastros que volta para a tela inicial
        CardLayout cl = (CardLayout) Panel_Main.getLayout();
        cl.show(Panel_Main, "Welcome");
    }//GEN-LAST:event_Btn_Register_VoltarActionPerformed

    private void CB_Register_TipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CB_Register_TipoActionPerformed
        //combo-box da tela de registro que da opcao de funcionario ou alunos para o cadastro
        switch(CB_Register_Tipo.getSelectedIndex()){
            case 0:
                jLabel8.setEnabled(false);
                Txt_Register_RAeCOO.setEnabled(false);
                break;
            case 1:
                jLabel8.setEnabled(true);
                Txt_Register_RAeCOO.setEnabled(true);
                break;            
            case 2:
                jLabel8.setEnabled(true);
                Txt_Register_RAeCOO.setEnabled(true);
                break;
        }
    }//GEN-LAST:event_CB_Register_TipoActionPerformed

    private void Btn_Register_ConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Register_ConfirmarActionPerformed
        //botao que confirma o cadastro na tela de cadastro
        boolean exist = false;
        switch(CB_Register_Tipo.getSelectedIndex()){//pega o index do que esta na combo box para criar a pessoa
            case 0://se for funcionario
                if(!Txt_Register_Nome.getText().equals("") && !Txt_Register_Email.getText().equals("") && !Txt_Register_Password.getText().equals("")){//campos preenchidos ou nao
                    Person person = new Employee(Txt_Register_Nome.getText(), Txt_Register_Email.getText(), Txt_Register_Password.getText());
                    for(int j = 0; j<personList.size(); j++){
                        if(personList.get(j).getEmail().equals(person.getEmail()))//se ja existe nao deixa registrar o mesmo email
                            exist = true;
                    }
                    if(!exist){//se nao existe ainda cria a pessoa e adiciona na lista de pessoas
                        personList.add(person);
                        modoFile = 0;
                        DataRecorder(modoFile, personList);//manda para ser escrito no arquivo
                        JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
                        CardLayout cl = (CardLayout) Panel_Main.getLayout();
                        cl.show(Panel_Main, "Welcome");
                        Txt_Register_Nome.setText("");
                        Txt_Register_Email.setText("");
                        Txt_Register_Password.setText("");
                    }
                    else{//erro ao tentar cadastrar o mesmo email
                        JOptionPane.showMessageDialog(this, "Esse e-mail já está cadastrado");
                        Txt_Register_Nome.setText("");
                        Txt_Register_Email.setText("");
                        Txt_Register_Password.setText("");                        
                    }
                    break;
                }
            case 1://criar um estudante de gradacao
                if(!Txt_Register_Nome.getText().equals("") && !Txt_Register_Email.getText().equals("") && !Txt_Register_Password.getText().equals("") && !Txt_Register_RAeCOO.getText().equals("")){
                    Person person = new Grad_student(Txt_Register_Nome.getText(), Txt_Register_Email.getText(), Txt_Register_Password.getText(), Txt_Register_RAeCOO.getText());
                    for(int j = 0; j<personList.size(); j++){
                        if(personList.get(j).getEmail().equals(person.getEmail()))
                            exist = true;
                    }
                    if(!exist){//adiciona na lista e escreve no arquivo
                        personList.add(person);
                        modoFile = 0;
                        DataRecorder(modoFile, personList);                        
                        JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
                        CardLayout cl = (CardLayout) Panel_Main.getLayout();
                        cl.show(Panel_Main, "Welcome");
                        Txt_Register_Nome.setText("");
                        Txt_Register_Email.setText("");
                        Txt_Register_Password.setText("");
                        Txt_Register_RAeCOO.setText("");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Esse e-mail já está cadastrado");
                        Txt_Register_Nome.setText("");
                        Txt_Register_Email.setText("");
                        Txt_Register_Password.setText("");  
                        Txt_Register_RAeCOO.setText("");
                    }
                    break;
            
                }
            case 2: //aluno de pos graduacao   
                if(!Txt_Register_Nome.getText().equals("") && !Txt_Register_Email.getText().equals("") && !Txt_Register_Password.getText().equals("") && !Txt_Register_RAeCOO.getText().equals("")){
                    Person person = new Postgrad_student(Txt_Register_Nome.getText(), Txt_Register_Email.getText(), Txt_Register_Password.getText(), Txt_Register_RAeCOO.getText());
                    for(int j = 0; j<personList.size(); j++){
                        if(personList.get(j).getEmail().equals(person.getEmail()))
                            exist = true;
                    }
                    if(!exist){//cria e escreve no arquivo
                        personList.add(person);
                        modoFile = 0;
                        DataRecorder(modoFile, personList);
                        JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
                        CardLayout cl = (CardLayout) Panel_Main.getLayout();
                        cl.show(Panel_Main, "Welcome");
                        Txt_Register_Nome.setText("");
                        Txt_Register_Email.setText("");
                        Txt_Register_Password.setText("");
                        Txt_Register_RAeCOO.setText("");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Esse e-mail já está cadastrado");
                        Txt_Register_Nome.setText("");
                        Txt_Register_Email.setText("");
                        Txt_Register_Password.setText("");  
                        Txt_Register_RAeCOO.setText("");
                    }
                    break;                   
                }
        }
    }//GEN-LAST:event_Btn_Register_ConfirmarActionPerformed

    private void Btn_Login_ConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Login_ConfirmarActionPerformed
        //botao para login
        for(int i = 0; i < personList.size();i++){//percorre a lista para fazer o login
            if(Txt_Login_Email.getText().equals(personList.get(i).getEmail())){//confere se o email esta certo
                if(Txt_Login_Password.getText().equals(personList.get(i).getPassword())){//confere se a senha esta certa
                    if(personList.get(i) instanceof Employee){//se esta tudo certo, manda para a tela do funcionario
                        emailHolder = Txt_Login_Email.getText();//segura o email de quem esta logado
                        JOptionPane.showMessageDialog(this, personList.get(i).getPersonName()+" bem-vindo!");
                        CardLayout cl = (CardLayout) Panel_Main.getLayout();
                        cl.show(Panel_Main, "Employee");
                        Txt_Login_Email.setText("");
                        Txt_Login_Password.setText("");
                        Btn_Employee_Publisher_Salvar.setEnabled(false);
                        Btn_Employee_Publisher_Cancelar.setEnabled(false);
                        Txt_Employee_AddressPublisher.setEnabled(false);
                        Txt_Employee_NamePublisher.setEnabled(false);                        
                        Btn_Employee_Books_Salvar.setEnabled(false);
                        Btn_Employee_Books_Cancelar.setEnabled(false);
                        Txt_Employee_BookAuthor.setEnabled(false);
                        Txt_Employee_BookTitle.setEnabled(false);
                        CB_Employee_Publishers.setEnabled(false);
                        LoadTablePublisher();//carrega a tabela de editoras na pag do funcionario
                        modo = 0;
                        LoadTableBooks();//carrega a tabela de livros na pag do funcionario
                        break;//esse break faz com que o for pare de checar outras contas caso ache a correta
                    }
                    else{
                        emailHolder = Txt_Login_Email.getText();//manda para a tela dos alunos. Os funcionaris e os alunos tem o login baseado em suas subclasses
                        JOptionPane.showMessageDialog(this, personList.get(i).getPersonName()+" bem-vindo!");
                        CardLayout cl = (CardLayout) Panel_Main.getLayout();
                        cl.show(Panel_Main, "UserInterface");
                        Txt_Login_Email.setText("");
                        Txt_Login_Password.setText("");
                        modo = 1;
                        LoadTableBooks();//carrega a tabela de todos os livros na tela do usuario
                        LoadTableMyBooks();//carrega a tabela de livros que a pessoa reservou
                        break;//esse break faz com que o for pare de checar outras contas caso ache a correta
                    }

                }
                else{//quando as infos nao estao corretas manda a mensagem de erro e reseta os campos preenchidos
                                JOptionPane.showMessageDialog(this, "E-mail ou Password inválido!");
                                Txt_Login_Email.setText("");
                                Txt_Login_Password.setText("");    
                }
            }
        }    
    }//GEN-LAST:event_Btn_Login_ConfirmarActionPerformed

    private void Btn_UserInterface_VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_UserInterface_VoltarActionPerformed
        //botao na tela de usuarios que volta para a tela inicial
        CardLayout cl = (CardLayout) Panel_Main.getLayout();
        cl.show(Panel_Main, "Welcome"); 
    }//GEN-LAST:event_Btn_UserInterface_VoltarActionPerformed

    private void Btn_Employee_VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Employee_VoltarActionPerformed
        //botao na tela de funcionaris que volta para a tela inicial
        CardLayout cl = (CardLayout) Panel_Main.getLayout();
        cl.show(Panel_Main, "Welcome"); 
    }//GEN-LAST:event_Btn_Employee_VoltarActionPerformed

    private void Btn_Employee_Publisher_NovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Employee_Publisher_NovoActionPerformed
        //botao que ativa alguns campos para entrar informacoes
        Txt_Employee_AddressPublisher.setText("");
        Txt_Employee_NamePublisher.setText("");
        Btn_Employee_Publisher_Salvar.setEnabled(true);
        Btn_Employee_Publisher_Cancelar.setEnabled(true);
        Txt_Employee_AddressPublisher.setEnabled(true);
        Txt_Employee_NamePublisher.setEnabled(true);
        Btn_Employee_Publisher_Novo.setEnabled(false);
        Btn_Employee_Publisher_Excluir.setEnabled(false);
    }//GEN-LAST:event_Btn_Employee_Publisher_NovoActionPerformed

    private void Btn_Employee_Publisher_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Employee_Publisher_CancelarActionPerformed
        //botao que desativa campos de entrada de informacao
        Txt_Employee_AddressPublisher.setEnabled(false);
        Txt_Employee_NamePublisher.setEnabled(false);
        Btn_Employee_Publisher_Salvar.setEnabled(false);
        Btn_Employee_Publisher_Cancelar.setEnabled(false);
        Txt_Employee_NamePublisher.setText("");
        Txt_Employee_AddressPublisher.setText("");
        Btn_Employee_Publisher_Novo.setEnabled(true);
        Btn_Employee_Publisher_Excluir.setEnabled(true);
    }//GEN-LAST:event_Btn_Employee_Publisher_CancelarActionPerformed

    private void Btn_Employee_Publisher_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Employee_Publisher_SalvarActionPerformed
        //botao que salva a editora e seu endereco na lista, e na tabela. Tambem escreve no arquivo
        Publisher publisher = new Publisher(Txt_Employee_NamePublisher.getText(), Txt_Employee_AddressPublisher.getText());
        publisherList.add(publisher);
        LoadTablePublisher();
        modoFile = 1;
        DataRecorder(modoFile, publisherList);
        Txt_Employee_NamePublisher.setText("");
        Txt_Employee_AddressPublisher.setText("");
        Btn_Employee_Publisher_Salvar.setEnabled(false);
        Btn_Employee_Publisher_Cancelar.setEnabled(false);
        Txt_Employee_AddressPublisher.setEnabled(false);
        Txt_Employee_NamePublisher.setEnabled(false);
        Btn_Employee_Publisher_Novo.setEnabled(true);
        Btn_Employee_Publisher_Excluir.setEnabled(true);
    }//GEN-LAST:event_Btn_Employee_Publisher_SalvarActionPerformed

    private void Table_Employee_PublisherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_Employee_PublisherMouseClicked
        //quando clica na tabela libera alguns campos para poder entrar informacoes
        int index = Table_Employee_Publisher.getSelectedRow();
        if(index >=0 && index<publisherList.size()){
            Publisher pub = publisherList.get(index);
            Btn_Employee_Publisher_Novo.setEnabled(true);
            Btn_Employee_Publisher_Excluir.setEnabled(true);
        }
        

    }//GEN-LAST:event_Table_Employee_PublisherMouseClicked

    private void Table_Employee_BooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_Employee_BooksMouseClicked
        //quando clica na tabela libera alguns campos para poder entrar informacoes
        int index = Table_Employee_Books.getSelectedRow();
        if(index >=0 && index<publisherList.size()){
            Publisher pub = publisherList.get(index);
            Btn_Employee_Books_Novo.setEnabled(true);
            Btn_Employee_Books_Excluir.setEnabled(true);
        }
        

    }//GEN-LAST:event_Table_Employee_BooksMouseClicked

    private void Btn_Employee_Books_NovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Employee_Books_NovoActionPerformed
        //libera os campos para poder entrar informacoes sobre os livros na tela do funcionario
        Txt_Employee_BookAuthor.setText("");
        Txt_Employee_BookTitle.setText("");
        Btn_Employee_Books_Salvar.setEnabled(true);
        Btn_Employee_Books_Cancelar.setEnabled(true);
        Txt_Employee_BookAuthor.setEnabled(true);
        Txt_Employee_BookTitle.setEnabled(true);
        Btn_Employee_Books_Novo.setEnabled(false);
        Btn_Employee_Books_Excluir.setEnabled(false);
        CB_Employee_Publishers.setEnabled(true);
    }//GEN-LAST:event_Btn_Employee_Books_NovoActionPerformed

    private void Btn_Employee_Books_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Employee_Books_SalvarActionPerformed
        //botao que salva os dados inseridos em suas respectivas listas e escreve as informacoes no arquivo
        //botao referente a tela de funcionarios para salvar livros
        int index = CB_Employee_Publishers.getSelectedIndex();//pega o indice de qual editora que vai receber o livro a ser criado
        if(index== 0){//caso nenhum for selecionada, mensagem de erro
            JOptionPane.showMessageDialog(this, "Selecione uma editora!");
        }
        else{
            Book b = new Book();
            b.setAuthor(Txt_Employee_BookAuthor.getText());
            b.setTitle(Txt_Employee_BookTitle.getText());
            b.setAvailable("Sim");
            b.setPublisher(publisherList.get(index-1));// "-1" pois o indice "0" corresponde a "Selecionar" <- as editoras comecam no indicie 1
            bookList.add(b);            
            publisherList.get(index-1).addBook(b);
            modo = 0;//tabela de livros do funcionario
            LoadTableBooks();
            modoFile = 1;//para salvar os livros em suas respectivas editoras no arquivo de editoras
            DataRecorder(modoFile, publisherList);
            modoFile = 2;//para salvar os livros no arquivo de livros
            DataRecorder(modoFile, bookList);
            Txt_Employee_BookTitle.setText("");
            Txt_Employee_BookAuthor.setText("");
            Txt_Employee_BookTitle.setEnabled(false);
            Txt_Employee_BookAuthor.setEnabled(false);
            Btn_Employee_Books_Salvar.setEnabled(false);
            Btn_Employee_Books_Cancelar.setEnabled(false);
            Btn_Employee_Books_Novo.setEnabled(true);
            CB_Employee_Publishers.setEnabled(false);
            Btn_Employee_Books_Excluir.setEnabled(true);
        }
        
    }//GEN-LAST:event_Btn_Employee_Books_SalvarActionPerformed

    private void Btn_Employee_Books_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Employee_Books_CancelarActionPerformed
        //desabilita alguns campos ao apertar cancelar na pagina dos funcionarios, parte dos livros
        Txt_Employee_BookAuthor.setEnabled(false);
        Txt_Employee_BookTitle.setEnabled(false);
        Btn_Employee_Books_Salvar.setEnabled(false);
        Btn_Employee_Books_Cancelar.setEnabled(false);
        Txt_Employee_BookAuthor.setText("");
        Txt_Employee_BookTitle.setText("");
        Btn_Employee_Books_Novo.setEnabled(true);
        Btn_Employee_Books_Excluir.setEnabled(true);
        CB_Employee_Publishers.setEnabled(false);
    }//GEN-LAST:event_Btn_Employee_Books_CancelarActionPerformed

    private void Btn_Employee_Publisher_ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Employee_Publisher_ExcluirActionPerformed
        //botao para excluir editoras na pag dos funcionaris
        int index = Table_Employee_Publisher.getSelectedRow();
        if(index>=0 && index<publisherList.size()){//para garantir que o index do que foi clicado na tabela esta certo
            for(int i = 0; i<bookList.size(); i++){
                if(bookList.get(i).getPublisher().getPublisherName().equals(publisherList.get(index).getPublisherName())){//procura a editora
                    Book book = new Book();
                    book = bookList.get(i);
                    for(int j = 0; j < personList.size(); j++){
                        for(int k = 0; k < personList.get(j).getBookList().size(); k++){//procura os livros da editora reservados por pessoas
                            if(personList.get(j).getBookList().get(k).getTitle().equals(book.getTitle())){
                                personList.get(j).getBookList().remove(book);//remove os livros a serem deletados junto com a editora
                                modoFile = 0;
                                DataRecorder(modoFile, personList);//atualiza o arquivo de pessoas
                            }
                        }
                    }
                    bookList.remove(i);
                    modoFile = 2;
                    DataRecorder(modoFile, bookList);//atualiza o arquivo de livros
                }  
            }
           publisherList.remove(index); 
           modoFile = 1;
            DataRecorder(modoFile, publisherList);//atualiza o arquivo de editoras
        }
        //atualiza as tabelas
        LoadTablePublisher();
        modo = 0;
        LoadTableBooks();
        modo = 1;
        LoadTableBooks();
        LoadTableMyBooks();
    }//GEN-LAST:event_Btn_Employee_Publisher_ExcluirActionPerformed

    private void Btn_Employee_Books_ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Employee_Books_ExcluirActionPerformed
        //botao na tela de funcionarios para excluir livros
        int index = Table_Employee_Books.getSelectedRow();
        if(index>=0 && index<publisherList.size()){
            Book book = new Book();
            book = bookList.get(index);
            for(int i = 0; i<publisherList.size(); i++){
                if(publisherList.get(i).getBookList().contains(book)){//ve se existe o livro a ser excluido dentro de alguma editora
                    publisherList.get(i).getBookList().remove(book);//remove o livro da lista de editoras
                    modoFile = 1;
                    DataRecorder(modoFile, publisherList);
                }  
            }
            for(int i = 0; i<personList.size(); i++){
                if(personList.get(i).getBookList().contains(book)){//ve se alguma pessoa possui o livro
                    personList.get(i).getBookList().remove(book);//remove da lista de livros da pessoa
                    modoFile = 0;
                    DataRecorder(modoFile, personList);
                }
            }
            bookList.remove(book);//remove da lista de livros
            modoFile = 2;
            DataRecorder(modoFile, bookList);
        }
        //carrega as tabelas
        LoadTablePublisher();
        modo = 0;
        LoadTableBooks();
        modo = 1;
        LoadTableBooks();
        LoadTableMyBooks();
    }//GEN-LAST:event_Btn_Employee_Books_ExcluirActionPerformed

    private void Btn_UserInterface_ProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_UserInterface_ProcurarActionPerformed
        //botao de procurar livros do usuario
        modo = 2;
        LoadTableBooks();
    }//GEN-LAST:event_Btn_UserInterface_ProcurarActionPerformed

    private void Btn_UserInterface_CancelarPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_UserInterface_CancelarPesquisaActionPerformed
        //botao de cancelar pesquisa
        Txt_UserInterface_Procurar.setText("");
    }//GEN-LAST:event_Btn_UserInterface_CancelarPesquisaActionPerformed

    private void Table_UserInterface_MyBooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_UserInterface_MyBooksMouseClicked

    }//GEN-LAST:event_Table_UserInterface_MyBooksMouseClicked

    private void Btn_UserInterface_DevolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_UserInterface_DevolverActionPerformed
        //botao da pag de usuarios para devolver livros
        int index = Table_UserInterface_MyBooks.getSelectedRow();
        Book book = new Book();
        int indexHolder = 0;
        
        for(int i = 0; i<personList.size();i++){
            if(emailHolder.equals(personList.get(i).getEmail()))
            {
                for(int j = 0; j<bookList.size(); j++){
                    if(personList.get(i).getBookList().get(index).getTitle().equals(bookList.get(j).getTitle())){//compara os titulos
                        indexHolder = j;//segura o indice para reover da lista de livros
                        book = personList.get(i).getBookList().get(index);//para poder remover esse livro especifico
                    }
                }
            }
        }
        
        for(int i = 0;i < personList.size(); i++){
            if(emailHolder.equals(personList.get(i).getEmail())){//confere o login da pessoa para acessar a lista certa
                if(personList.get(i) instanceof Grad_student){//se for graduacao
                    if(personList.get(i).getBookList().contains(book)){
                        personList.get(i).getBookList().remove(book);
                        bookList.get(indexHolder).setAvailable("Sim");
                        modoFile = 0; 
                        DataRecorder(modoFile, personList);
                        modoFile = 2;
                        DataRecorder(modoFile, bookList);
                        modo = 1;
                        LoadTableBooks();
                        LoadTableMyBooks();
                        modo = 0;
                        LoadTableBooks();
                        JOptionPane.showMessageDialog(this, "Livro devolvido!");
                    }              
                }
                else if(personList.get(i) instanceof Postgrad_student){//se for da pos
                        personList.get(i).getBookList().remove(book);
                        bookList.get(indexHolder).setAvailable("Sim");
                        modoFile = 0; 
                        DataRecorder(modoFile, personList);
                        modoFile = 2;
                        DataRecorder(modoFile, bookList);
                        modo = 1;
                        LoadTableBooks();
                        LoadTableMyBooks();
                        modo = 0;
                        LoadTableBooks();
                        JOptionPane.showMessageDialog(this, "Livro devolvido!");
                    
                }
            }
        }            
    }//GEN-LAST:event_Btn_UserInterface_DevolverActionPerformed

    private void Table_UserInterface_SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_UserInterface_SearchMouseClicked

    }//GEN-LAST:event_Table_UserInterface_SearchMouseClicked

    private void Btn_UserInterface_ReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_UserInterface_ReservarActionPerformed
        //botao de reserva livros
        int index = Table_UserInterface_Search.getSelectedRow();
        for(int i = 0;i < personList.size(); i++){
            if(emailHolder.equals(personList.get(i).getEmail())){
                if(personList.get(i) instanceof Grad_student){//grad
                    if(personList.get(i).getBookList().size()<5 && bookList.get(index).getAvailable().equals("Sim")){//confere se cabe na lista do estdante e se o livro esta disponivel
                        personList.get(i).getBookList().add(bookList.get(index));
                        bookList.get(index).setAvailable("Não");
                        modoFile = 0;//arquivo das pessoas 
                        DataRecorder(modoFile, personList);
                        modoFile = 2;//arquivo dos livros
                        DataRecorder(modoFile, bookList);
                        modo = 1;
                        LoadTableBooks();
                        modo = 0;
                        LoadTableBooks();
                        LoadTableMyBooks();
                        JOptionPane.showMessageDialog(this, "Livro reservado!");
                        
                    }
                    else{//caso nao seja possivel reservar
                        JOptionPane.showMessageDialog(this, "Sua lista de livros esta cheia, ou esse livro não está disponível!");
                    }
                }
                else if(personList.get(i) instanceof Postgrad_student){//pos grad
                        if(personList.get(i).getBookList().size()<10 && bookList.get(index).getAvailable().equals("Sim")){
                        personList.get(i).getBookList().add(bookList.get(index));
                        bookList.get(index).setAvailable("Não");
                        modoFile = 0; 
                        DataRecorder(modoFile, personList);
                        modoFile = 2;
                        DataRecorder(modoFile, bookList);
                        modo = 1;
                        LoadTableBooks();
                        modo = 0;
                        LoadTableBooks();
                        LoadTableMyBooks();
                        JOptionPane.showMessageDialog(this, "Livro reservado!");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Sua lista de livros esta cheia, ou esse livro não está disponível!");
                    }
                }
            }
        }            
    }//GEN-LAST:event_Btn_UserInterface_ReservarActionPerformed

    private void Txt_Register_RAeCOOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Txt_Register_RAeCOOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Txt_Register_RAeCOOActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Form_Main().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Form_Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Employee_Books_Cancelar;
    private javax.swing.JButton Btn_Employee_Books_Excluir;
    private javax.swing.JButton Btn_Employee_Books_Novo;
    private javax.swing.JButton Btn_Employee_Books_Salvar;
    private javax.swing.JButton Btn_Employee_Publisher_Cancelar;
    private javax.swing.JButton Btn_Employee_Publisher_Excluir;
    private javax.swing.JButton Btn_Employee_Publisher_Novo;
    private javax.swing.JButton Btn_Employee_Publisher_Salvar;
    private javax.swing.JButton Btn_Employee_Voltar;
    private javax.swing.JButton Btn_Login_Confirmar;
    private javax.swing.JButton Btn_Login_Voltar;
    private javax.swing.JButton Btn_Register_Confirmar;
    private javax.swing.JButton Btn_Register_Voltar;
    private javax.swing.JButton Btn_UserInterface_CancelarPesquisa;
    private javax.swing.JButton Btn_UserInterface_Devolver;
    private javax.swing.JButton Btn_UserInterface_Procurar;
    private javax.swing.JButton Btn_UserInterface_Reservar;
    private javax.swing.JButton Btn_UserInterface_Voltar;
    private javax.swing.JButton Btn_Welcome_Cadastrar;
    private javax.swing.JButton Btn_Welcome_Entrar;
    private javax.swing.JComboBox<String> CB_Employee_Publishers;
    private javax.swing.JComboBox<String> CB_Register_Tipo;
    private javax.swing.JPanel Panel_Employee;
    private javax.swing.JPanel Panel_Login;
    private javax.swing.JPanel Panel_Main;
    private javax.swing.JPanel Panel_Register;
    private javax.swing.JPanel Panel_UserInterface;
    private javax.swing.JPanel Panel_Welcome;
    private javax.swing.JTable Table_Employee_Books;
    private javax.swing.JTable Table_Employee_Publisher;
    private javax.swing.JTable Table_UserInterface_MyBooks;
    private javax.swing.JTable Table_UserInterface_Search;
    private javax.swing.JTextField Txt_Employee_AddressPublisher;
    private javax.swing.JTextField Txt_Employee_BookAuthor;
    private javax.swing.JTextField Txt_Employee_BookTitle;
    private javax.swing.JTextField Txt_Employee_NamePublisher;
    private javax.swing.JTextField Txt_Login_Email;
    private javax.swing.JPasswordField Txt_Login_Password;
    private javax.swing.JTextField Txt_Register_Email;
    private javax.swing.JTextField Txt_Register_Nome;
    private javax.swing.JPasswordField Txt_Register_Password;
    private javax.swing.JTextField Txt_Register_RAeCOO;
    private javax.swing.JTextField Txt_UserInterface_Procurar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane4;
    // End of variables declaration//GEN-END:variables
}
