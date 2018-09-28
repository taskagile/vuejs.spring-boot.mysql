package com.taskagile.web.apis;

import com.taskagile.domain.application.CardService;
import com.taskagile.domain.application.commands.*;
import com.taskagile.domain.common.file.FileUrlCreator;
import com.taskagile.domain.model.activity.Activity;
import com.taskagile.domain.model.attachment.Attachment;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.web.payload.*;
import com.taskagile.web.results.*;
import com.taskagile.web.updater.CardUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CardApiController extends AbstractBaseController {

  private CardService cardService;
  private CardUpdater cardUpdater;
  private FileUrlCreator fileUrlCreator;

  public CardApiController(CardService cardService,
                           CardUpdater cardUpdater,
                           FileUrlCreator fileUrlCreator) {
    this.cardService = cardService;
    this.cardUpdater = cardUpdater;
    this.fileUrlCreator = fileUrlCreator;
  }

  @PostMapping("/api/cards")
  public ResponseEntity<ApiResult> addCard(@RequestBody AddCardPayload payload,
                                           HttpServletRequest request) {
    AddCardCommand command = payload.toCommand();
    addTriggeredBy(command, request);

    Card card = cardService.addCard(command);
    cardUpdater.onCardAdded(payload.getBoardId(), card);
    return AddCardResult.build(card);
  }

  @GetMapping("/api/cards/{cardId}")
  public ResponseEntity<ApiResult> getCard(@PathVariable long cardId) {
    Card card = cardService.findById(new CardId(cardId));
    return CardResult.build(card);
  }

  @PostMapping("/api/cards/positions")
  public ResponseEntity<ApiResult> changeCardPositions(@RequestBody ChangeCardPositionsPayload payload,
                                                       HttpServletRequest request) {
    ChangeCardPositionsCommand command = payload.toCommand();
    addTriggeredBy(command, request);

    cardService.changePositions(command);
    return Result.ok();
  }

  @PutMapping("/api/cards/{cardId}/title")
  public ResponseEntity<ApiResult> changeTitle(@PathVariable long cardId,
                                               @RequestBody ChangeCardTitlePayload payload,
                                               HttpServletRequest request) {
    ChangeCardTitleCommand command = payload.toCommand(cardId);
    addTriggeredBy(command, request);

    cardService.changeCardTitle(command);
    return Result.ok();
  }

  @PutMapping("/api/cards/{cardId}/description")
  public ResponseEntity<ApiResult> changeDescription(@PathVariable long cardId,
                                                     @RequestBody ChangeCardDescriptionPayload payload,
                                                     HttpServletRequest request) {
    ChangeCardDescriptionCommand command = payload.toCommand(cardId);
    addTriggeredBy(command, request);

    cardService.changeCardDescription(command);
    return Result.ok();
  }

  @PostMapping("/api/cards/{cardId}/comments")
  public ResponseEntity<ApiResult> addCardComment(@PathVariable long cardId,
                                                  @RequestBody AddCardCommentPayload payload,
                                                  HttpServletRequest request) {
    AddCardCommentCommand command = payload.toCommand(new CardId(cardId));
    addTriggeredBy(command, request);

    Activity activity = cardService.addComment(command);
    return CommentActivityResult.build(activity);
  }

  @GetMapping("/api/cards/{cardId}/activities")
  public ResponseEntity<ApiResult> getCardActivities(@PathVariable long cardId) {
    List<Activity> activities = cardService.findCardActivities(new CardId(cardId));
    return CardActivitiesResult.build(activities);
  }

  @PostMapping("/api/cards/{cardId}/attachments")
  public ResponseEntity<ApiResult> addAttachment(@PathVariable long cardId,
                                                 @RequestParam("file") MultipartFile file,
                                                 HttpServletRequest request) {
    AddCardAttachmentCommand command = new AddCardAttachmentCommand(cardId, file);
    addTriggeredBy(command, request);

    Attachment attachment = cardService.addAttachment(command);
    return AttachmentResult.build(attachment, fileUrlCreator);
  }

  @GetMapping("/api/cards/{cardId}/attachments")
  public ResponseEntity<ApiResult> getAttachments(@PathVariable long cardId) {
    List<Attachment> attachments = cardService.getAttachments(new CardId(cardId));
    return AttachmentResults.build(attachments, fileUrlCreator);
  }
}
