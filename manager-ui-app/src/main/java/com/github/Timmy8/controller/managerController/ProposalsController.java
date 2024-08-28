package com.github.Timmy8.controller.managerController;

import com.github.Timmy8.controller.payload.NewClientPayload;
import com.github.Timmy8.controller.payload.NewProposalPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.ClientsRestClient;
import com.github.Timmy8.restClient.interfaces.ProposalsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager/proposals")
public class ProposalsController {
    private final ProposalsRestClient restClient;

    @GetMapping("list")
    public String getProposalsListPage(Model model){
        model.addAttribute("proposals", restClient.findAllProposals());

        return "manager/proposals/list";
    }

    @GetMapping("create")
    public String getCreateProposalPage(){
        return "manager/proposals/new_proposal";
    }

    @PostMapping("create")
    public String createProposals(NewProposalPayload payload, Model model){
        try{
            Proposal proposal = restClient.createProposal(payload.name(), payload.description(), payload.price());

            return "redirect:/manager/proposals/list";
        } catch (BadRequestException exception){
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());

            return "manager/proposals/new_proposal";
        }
    }
}
