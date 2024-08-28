package com.github.Timmy8.controller.managerController;

import com.github.Timmy8.controller.payload.UpdateClientPayload;
import com.github.Timmy8.controller.payload.UpdateProposalPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.ClientsRestClient;
import com.github.Timmy8.restClient.interfaces.ProposalsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager/proposals/{proposalId:\\d+}")
public class ProposalController {
    private final ProposalsRestClient restClient;

    @ModelAttribute
    public Proposal proposal(@PathVariable("proposalId") int proposalId){
        return restClient.findProposal(proposalId)
                .orElseThrow(() -> new NoSuchElementException("api.proposal.create.errors.proposal_not_found"));
    }

    @GetMapping
    public String getProposalPage(){
        return "manager/proposals/proposal";
    }

    @GetMapping("edit")
    public String getEditProposalPage(){
        return "manager/proposals/edit";
    }

    @PostMapping("edit")
    public String updateProposal(@ModelAttribute(name = "proposal", binding = false) Proposal proposal,
                               UpdateProposalPayload payload,
                               Model model){
        try{
            restClient.updateProposal(proposal.id(), payload.name(), payload.description(), payload.price());

            return "redirect:/manager/proposals/%d".formatted(proposal.id());
        } catch (BadRequestException exception){
            model.addAttribute("errors", exception.getErrors());
            model.addAttribute("payload", payload);

            return "manager/proposals/edit";
        }
    }

    @PostMapping("delete")
    public String deleteProposal(@ModelAttribute("proposal") Proposal proposal){
        restClient.deleteProposal(proposal.id());

        return "redirect:/manager/proposals/list";
    }
}
