package eaj.ufrn.br.ecommercecomputadores.service;

import eaj.ufrn.br.ecommercecomputadores.domain.Computador;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class FileStorageService {
    private final String upload = "src/main/webapp/WEB-INF/uploads";
    private final Path root = Paths.get("src/main/webapp/WEB-INF/images");
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public String update(MultipartFile file, Computador computador){

        try {
            int indexNomeImagem = computador.getImageUri().lastIndexOf("/");
            String nomeImagem = computador.getImageUri().substring(indexNomeImagem);
            Path pathImageOriginal =  Paths.get(this.root.toString() + nomeImagem);
            Files.delete(pathImageOriginal);

            return save(file);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public String save(MultipartFile file) {
        try {
            Path pathTemporario = Paths.get(upload +"\\"+ file.getOriginalFilename());
            System.out.println(pathTemporario);
            file.transferTo(pathTemporario);

            String nomeOriginal = file.getOriginalFilename();
            String nomeImagem = UUID.randomUUID().toString();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
            String novoNome = nomeImagem+extensao;
            Path novaImagem = Paths.get(this.root.toUri().resolve(novoNome));
            Files.move(pathTemporario, novaImagem, StandardCopyOption.REPLACE_EXISTING);

            return novoNome;
      } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
