package ru.itis.nishesi.MyTube.restcontrollers.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.ViewDto;
import ru.itis.nishesi.MyTube.dto.rest.ExceptionDto;
import ru.itis.nishesi.MyTube.dto.rest.ReactionForm;
import ru.itis.nishesi.MyTube.validation.rest.responses.ValidationErrorsDto;

import java.util.UUID;

@Tags(value = {
        @Tag(name = "Reactions")
})
@RequestMapping("/user")
@Schema(description = "Work with reactions")
public interface ReactionApi {
    @Operation(summary = "Add reaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "reaction",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ViewDto.class))
                    }
            ),
            @ApiResponse(responseCode = "422", description = "invalid input",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorsDto.class))
                    }
            ),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthenticated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @PostMapping
    ResponseEntity<?> createReaction(@Parameter(description = "reaction form")
                                     ReactionForm reactionForm,
                                     @Parameter(hidden = true)
                                     UserDto user);

    @Operation(summary = "Get reaction with views")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "view",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ViewDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "view not found"
            ),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthenticated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @GetMapping("/{videoId}")
    ResponseEntity<?> getReaction(@PathVariable
                                  @Parameter(description = "id of video")
                                  UUID videoId,
                                  @Parameter(hidden = true)
                                  UserDto user);

    @Operation(summary = "update or save reaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "reaction and views",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ViewDto.class))
                    }
            ),
            @ApiResponse(responseCode = "422", description = "невалидные данные",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationErrorsDto.class))
                    }
            ),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthenticated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @PutMapping
    ViewDto updateReaction(@Parameter(description = "reaction form")
                           ReactionForm reactionForm,
                           @Parameter(hidden = true)
                           UserDto user);

    @Operation(summary = "delete reaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reaction deleted"),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthenticated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @DeleteMapping("/{videoId}")
    ResponseEntity<?> deleteReaction(@Parameter(description = "video id")
                                     @PathVariable
                                     UUID videoId,
                                     @Parameter(hidden = true)
                                     UserDto user);
}
