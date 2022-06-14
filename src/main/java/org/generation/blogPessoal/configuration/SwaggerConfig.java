package org.generation.blogPessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {
  
  @Bean
  public OpenAPI springBlogPessoaOpenAPI(){
    return new OpenAPI()
    .info(new Info()
      .title("Projeto Blog Pessoal")
      .description("Projeto Blog Pessoal- Generation Brasil")
      .version("v0.0.1")
    .license(new License()
      .name("Conteudo Generation")
      .url("https://brasil.generation.org/"))
    .contact(new Contact()
      .name("Lairton da Silva")
      .url("https://github.com/Lairtondasilva")
      .email("lairtondasilva07@gmail.com")))
      .externalDocs(new io.swagger.v3.oas.models.ExternalDocumentation()
      .description("GitHub")
      .url("https://github.com/Lairtondasilva"));
    }
    @Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));

			}));
		};
	}
  
  private ApiResponse createApiResponse(String message) { //Método 'create' add uma msg em caa resposta Http!
        return new ApiResponse().description(message);
    }

}
